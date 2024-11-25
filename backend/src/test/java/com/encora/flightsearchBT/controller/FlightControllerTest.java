package com.encora.flightsearchBT.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.encora.flightsearchBT.models.Airport;
import com.encora.flightsearchBT.models.Flight;
import com.encora.flightsearchBT.models.FlightRequest;
import com.encora.flightsearchBT.models.PriceBreakdown;
import com.encora.flightsearchBT.service.AmadeusApiService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class FlightControllerTest {

    @Mock
    private AmadeusApiService amadeusApiService;

    @InjectMocks
    private FlightController flightController;

    @Test
    public void testGetFlightOffers() {
        FlightRequest flightRequest = new FlightRequest();
        flightRequest.setOrigin("NYC");
        flightRequest.setDestination("LAX");
        flightRequest.setDepartureDate(LocalDate.now());
        flightRequest.setReturnDate(LocalDate.now().plusDays(5));
        flightRequest.setAdults(1);
        flightRequest.setNonStop(true);
        flightRequest.setCurrencyCode("USD");

        try {
            when(amadeusApiService.getFlightOffers(anyString(), anyString(), anyString(), anyString(), anyInt(), anyBoolean(), anyString()))
                    .thenReturn(Collections.emptyList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ResponseEntity<List<Flight>> response = flightController.getFlightOffers(flightRequest);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testGetFlightOffersWithData() {
        FlightRequest flightRequest = new FlightRequest();
        flightRequest.setOrigin("NYC");
        flightRequest.setDestination("LAX");
        flightRequest.setDepartureDate(LocalDate.now());
        flightRequest.setReturnDate(LocalDate.now().plusDays(5));
        flightRequest.setAdults(1);
        flightRequest.setNonStop(true);
        flightRequest.setCurrencyCode("USD");

        Flight flight = new Flight();
        flight.setId("FL123");
        flight.setTotalFlightTime(Duration.ofHours(6));
        flight.setSegments(Collections.emptyList()); // Suponiendo que tienes una clase Segment
        flight.setPriceBreakdown(new PriceBreakdown()); // Suponiendo que tienes una clase PriceBreakdown

        List<Flight> flightOffers = List.of(flight);
        try {
            when(amadeusApiService.getFlightOffers(anyString(), anyString(), anyString(), anyString(), anyInt(), anyBoolean(), anyString()))
                    .thenReturn(flightOffers);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ResponseEntity<List<Flight>> response = flightController.getFlightOffers(flightRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
        assertEquals("FL123", response.getBody().get(0).getId());
    }

    @Test
    public void testGetFlightOffersThrowsException() {
        FlightRequest flightRequest = new FlightRequest();
        flightRequest.setOrigin("NYC");
        flightRequest.setDestination("LAX");
        flightRequest.setDepartureDate(LocalDate.now());
        flightRequest.setReturnDate(LocalDate.now().plusDays(5));
        flightRequest.setAdults(1);
        flightRequest.setNonStop(true);
        flightRequest.setCurrencyCode("USD");

        try {
            when(amadeusApiService.getFlightOffers(anyString(), anyString(), anyString(), anyString(), anyInt(), anyBoolean(), anyString()))
                    .thenThrow(new RuntimeException("Error fetching flight offers"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ResponseEntity<List<Flight>> response = flightController.getFlightOffers(flightRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


    @Test
    public void testGetAirports() {
        String keyword = "JFK";
        try {
            when(amadeusApiService.getAirportList(keyword)).thenReturn(Collections.emptyList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ResponseEntity<List<Airport>> response = flightController.getAirports(keyword);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }
}