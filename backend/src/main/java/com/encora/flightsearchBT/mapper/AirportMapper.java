package com.encora.flightsearchBT.mapper;

import com.encora.flightsearchBT.models.Airport;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AirportMapper {

    public List<Airport> mapAirportListResponse(String jsonResponse) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonResponse);

        if (!root.has("data") || root.path("data").isEmpty()) {
            throw new IllegalArgumentException("No airport data found in the response");
        }

        List<Airport> airports = new ArrayList<>();
        JsonNode data = root.path("data");

        for (JsonNode airportNode : data) {
            airports.add(mapSingleAirport(airportNode));
        }

        return airports;
    }

    public Airport mapSingleAirportResponse(String jsonResponse) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonResponse);

        if (!root.has("data") || root.path("data").isEmpty()) {
            throw new IllegalArgumentException("No airport data found in the response");
        }

        JsonNode airportNode = root.path("data").get(0);
        return mapSingleAirport(airportNode);
    }

    private Airport mapSingleAirport(JsonNode airportNode) {
        if (!airportNode.has("iataCode") || !airportNode.has("name") || !airportNode.has("address")) {
            throw new IllegalArgumentException("Incomplete airport data in the response");
        }

        Airport airport = new Airport();
        airport.setCode(airportNode.path("iataCode").asText());
        airport.setName(airportNode.path("name").asText());
        airport.setCity(airportNode.path("address").path("cityName").asText());
        airport.setCountry(airportNode.path("address").path("countryName").asText());

        return airport;
    }
}


