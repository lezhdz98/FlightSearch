package com.encora.flightsearchBT.service;

import com.encora.flightsearchBT.mapper.AirportMapper;
import com.encora.flightsearchBT.mapper.FlightOfferMapper;
import com.encora.flightsearchBT.models.Airport;
import com.encora.flightsearchBT.models.AmadeusAuthorization;
import com.encora.flightsearchBT.models.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AmadeusApiService {

    private static final int MAX_FLIGHT_OFFERS = 15;
    private static final int MAX_RETRIES = 3;
    private static final long INITIAL_BACKOFF = 1000L; // 1 sec

    @Autowired
    private TokenService tokenService;

    @Autowired
    private FlightOfferMapper flightOfferMapper;

    @Autowired
    private AirportMapper airportMapper;

    public AmadeusApiService (TokenService tokenService, FlightOfferMapper flightOfferMapper, AirportMapper airportMapper){
        this.tokenService= tokenService;
        this.flightOfferMapper = flightOfferMapper;
        this.airportMapper=airportMapper;
    }

    private final ConcurrentHashMap<String, Airport> airportCache = new ConcurrentHashMap<>();

    public Airport getSingleAirport(String keyword) throws Exception {
        if (airportCache.containsKey(keyword)) {
            return airportCache.get(keyword);
        }

        AmadeusAuthorization authorization = tokenService.requestToken();
        String accessToken = authorization.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = "https://test.api.amadeus.com/v1/reference-data/locations?subType=AIRPORT&keyword=" + keyword;

        RestTemplate restTemplate = new RestTemplate();
        int attempts = 0;
        long backoff = INITIAL_BACKOFF;

        while (attempts < MAX_RETRIES) {
            try {
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
                Airport airport = airportMapper.mapSingleAirportResponse(response.getBody());
                airportCache.put(keyword, airport);
                return airport;
            } catch (HttpClientErrorException.TooManyRequests e) {
                attempts++;
                if (attempts >= MAX_RETRIES) {
                    throw new RuntimeException("Exceeded maximum retries for fetching airport data", e);
                }
                Thread.sleep(backoff);
                backoff *= 2; // Exponential backoff
            }
        }
        throw new RuntimeException("Failed to fetch airport data after retries");
    }

    public List<Airport> getAirportList(String keyword) throws Exception {
        AmadeusAuthorization authorization = tokenService.requestToken();
        String accessToken = authorization.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = "https://test.api.amadeus.com/v1/reference-data/locations?subType=AIRPORT&keyword=" + keyword;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return airportMapper.mapAirportListResponse(response.getBody());
    }

    public List<Flight> getFlightOffers(String origin, String destination, String departureDate, String returnDate, int adults, boolean nonStop, String currencyCode) throws Exception {
        AmadeusAuthorization authorization = tokenService.requestToken();
        String accessToken = authorization.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = String.format("https://test.api.amadeus.com/v2/shopping/flight-offers?originLocationCode=%s&destinationLocationCode=%s&departureDate=%s&adults=%d&nonStop=%b&currencyCode=%s&max=%d",
                origin, destination, departureDate, adults, nonStop, currencyCode, MAX_FLIGHT_OFFERS);

        if (returnDate != null && !returnDate.isEmpty()) {
            url += "&returnDate=" + returnDate;
        }

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return flightOfferMapper.mapFlightOffersResponse(response.getBody());
    }
}