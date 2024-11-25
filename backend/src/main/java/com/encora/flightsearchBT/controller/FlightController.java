package com.encora.flightsearchBT.controller;

import com.encora.flightsearchBT.models.Airport;
import com.encora.flightsearchBT.models.Flight;
import com.encora.flightsearchBT.models.FlightRequest;
import com.encora.flightsearchBT.service.AmadeusApiService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:8080")
public class FlightController {

    private static final Logger logger = LoggerFactory.getLogger(FlightController.class);

    @Autowired
    private AmadeusApiService amadeusApiService;

    @GetMapping("/flight-offers")
    public ResponseEntity<List<Flight>> getFlightOffers(@Valid FlightRequest flightRequest) {
        if (flightRequest.getReturnDate() != null && flightRequest.getReturnDate().isBefore(flightRequest.getDepartureDate())) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            List<Flight> flightOffers = amadeusApiService.getFlightOffers(
                    flightRequest.getOrigin(),
                    flightRequest.getDestination(),
                    flightRequest.getDepartureDate().toString(),
                    flightRequest.getReturnDate() != null ? flightRequest.getReturnDate().toString() : null,
                    flightRequest.getAdults(),
                    flightRequest.isNonStop(),
                    flightRequest.getCurrencyCode()
            );

            if (flightOffers.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(flightOffers);

        } catch (Exception e) {
            logger.error("Error fetching flight offers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/airport")
    public ResponseEntity<List<Airport>> getAirports(@RequestParam String keyword) {
        try {
            List<Airport> airports = amadeusApiService.getAirportList(keyword);
            return ResponseEntity.ok(airports);
        } catch (Exception e) {
            logger.error("Error fetching airports", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
