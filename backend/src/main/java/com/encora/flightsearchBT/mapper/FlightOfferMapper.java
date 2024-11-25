package com.encora.flightsearchBT.mapper;

import com.encora.flightsearchBT.models.*;
import com.encora.flightsearchBT.service.AmadeusApiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class FlightOfferMapper {

    private static final Logger logger = LoggerFactory.getLogger(FlightOfferMapper.class);
    private final AmadeusApiService amadeusApiService;

    @Autowired
    public FlightOfferMapper(@Lazy AmadeusApiService amadeusApiService) {
        this.amadeusApiService = amadeusApiService;
    }

    // Method to map flight offers response from JSON string to a list of Flight objects
    public List<Flight> mapFlightOffersResponse(String jsonResponse) throws IOException {
        ObjectMapper mapper = new ObjectMapper(); // Create an ObjectMapper to parse JSON
        JsonNode root = mapper.readTree(jsonResponse); // Parse the JSON response

        // Check if the "data" field is present and not empty
        if (!root.has("data") || root.path("data").isEmpty()) {
            throw new IllegalArgumentException("No flight data found in the response");
        }

        JsonNode data = root.path("data"); // Access the "data" node
        JsonNode dictionaries = root.path("dictionaries"); // Access the "dictionaries" node
        List<Flight> flights = new ArrayList<>(); // Initialize a list to hold Flight objects

        // Iterate through each flight node in the "data" array
        for (JsonNode flightNode : data) {
            Flight flight = new Flight(); // Create a new Flight object
            flight.setId(flightNode.path("id").asText()); // Set the flight ID

            List<FlightSegment> segments = new ArrayList<>(); // Initialize a list to hold FlightSegment objects
            Duration totalFlightTime = Duration.ZERO; // Initialize total flight time to zero


            // Iterate through each itinerary node
            for (JsonNode itineraryNode : flightNode.path("itineraries")) {
                totalFlightTime = totalFlightTime.plus(Duration.parse(itineraryNode.path("duration").asText())); // Add the itinerary duration to the total flight time

                // Iterate through each segment node in the itinerary
                for (JsonNode segmentNode : itineraryNode.path("segments")) {
                    FlightSegment segment = new FlightSegment(); // Create a new FlightSegment object
                    segment.setId(segmentNode.path("id").asText()); // Set the segment ID
                    segment.setDepartureDateTime(LocalDateTime.parse(segmentNode.path("departure").path("at").asText())); // Set the departure date and time
                    segment.setArrivalDateTime(LocalDateTime.parse(segmentNode.path("arrival").path("at").asText())); // Set the arrival date and time


                    // getting IATA Code from the JSON and searching in the Amadeus API with the code
                    String departureIataCode = segmentNode.path("departure").path("iataCode").asText();
                    String arrivalIataCode = segmentNode.path("arrival").path("iataCode").asText();

                    try {
                        Airport departureAirport = amadeusApiService.getSingleAirport(departureIataCode);
                        Airport arrivalAirport = amadeusApiService.getSingleAirport(arrivalIataCode);
                        segment.setDepartureAirport(departureAirport);
                        segment.setArrivalAirport(arrivalAirport);
                    } catch (Exception e) {
                        logger.error("Error fetching airport data for IATA codes: {} and {}", departureIataCode, arrivalIataCode, e);
                        throw new RuntimeException("Error fetching airport data", e);
                    }

                    // Set the airline
                    Airline airline = new Airline();
                    String airlineCode = segmentNode.path("carrierCode").asText();
                    airline.setCode(airlineCode);
                    airline.setName(dictionaries.path("carriers").path(airlineCode).asText());
                    segment.setAirline(airline);

                    // Set the operating airline if present
                    if (segmentNode.has("operating")) {
                        Airline operatingAirline = new Airline();
                        String operatingCode = segmentNode.path("operating").path("carrierCode").asText();
                        operatingAirline.setCode(operatingCode);
                        operatingAirline.setName(dictionaries.path("carriers").path(operatingCode).asText());
                        segment.setOperatingAirline(operatingAirline);
                    }

                    segment.setLayoverTime(Duration.parse(segmentNode.path("duration").asText())); // Set the flight time
                    segment.setFlightNumber(segmentNode.path("number").asText()); // Set the flight number

                    String aircraftCode = segmentNode.path("aircraft").path("code").asText();
                    segment.setAircraftType(dictionaries.path("aircraft").path(aircraftCode).asText()); // Set the aircraft type


                    List<Stop> stops = new ArrayList<>(); // Initialize a list to hold Stop objects
                    // Iterate through each stop node if present
                    if (segmentNode.has("stops")) {
                        for (JsonNode stopNode : segmentNode.path("stops")) {
                            Stop stop = new Stop(); // Create a new Stop object



                            String stopIataCode = stopNode.path("iataCode").asText();

                            try {
                                Airport stopAirport = amadeusApiService.getSingleAirport(stopIataCode);
                                stop.setAirport(stopAirport);
                            } catch (Exception e) {
                                logger.error("Error fetching airport data for IATA code: {} ", stopIataCode, e);
                                throw new RuntimeException("Error fetching airport data", e);
                            }

                            stop.setDuration(Duration.parse(stopNode.path("duration").asText())); // Set the stop duration
                            stops.add(stop); // Add the stop to the list
                        }
                    }


                    segment.setStops(stops); // Set the stops for the segment
                    segments.add(segment); // Add the segment to the list
                }
            }
            flight.setSegments(segments); // Set the segments for the flight
            flight.setTotalFlightTime(totalFlightTime); // Set the total flight time for the flight

            // Access the fees array from the JSON
            JsonNode feesArray = flightNode.path("price").path("fees");
            BigDecimal totalFees = BigDecimal.ZERO; // Initialize total fees to zero

            // Iterate through each element in the fees array
            for (JsonNode feeNode : feesArray) {
                // Convert each fee amount to BigDecimal
                BigDecimal feeAmount = new BigDecimal(feeNode.path("amount").asText());
                // Add the fee amount to the total fees
                totalFees = totalFees.add(feeAmount);
            }

            PriceBreakdown priceBreakdown = new PriceBreakdown(); // Create a new PriceBreakdown object
            // Set the base price by converting the text value to BigDecimal
            priceBreakdown.setBasePrice(new BigDecimal(flightNode.path("price").path("base").asText()));
            // Set the total price by converting the text value to BigDecimal
            priceBreakdown.setTotalPrice(new BigDecimal(flightNode.path("price").path("total").asText()));
            // Set the total fees calculated from the fees array
            priceBreakdown.setFees(totalFees);
            flight.setPriceBreakdown(priceBreakdown); // Set the price breakdown for the flight

            // Initialize a list to hold TravelerPricing objects
            List<TravelerPricing> travelerPricings = new ArrayList<>();
            List<Amenity> amenities = new ArrayList<>();
            // Iterate through each traveler pricing node
            for (JsonNode travelerNode : flightNode.path("travelerPricings")) {
                TravelerPricing traveler = new TravelerPricing(); // Create a new TravelerPricing object
                traveler.setId(travelerNode.path("travelerId").asText()); // Set the traveler ID
                traveler.setTotalPrice(new BigDecimal(travelerNode.path("price").path("total").asText())); // Set the total price

                // Initialize a list to hold Amenity objects

                // Iterate through each fare details by segment node
                for (JsonNode fareDetailsNode : travelerNode.path("fareDetailsBySegment")) {

                    traveler.setCabin(fareDetailsNode.path("cabin").asText()); // Set the cabin option
                    traveler.setFlightClass(fareDetailsNode.path("class").asText()); // Set the class

                    for (JsonNode amenityNode : fareDetailsNode.path("amenities")) {
                        Amenity amenity = new Amenity(); // Create a new Amenity object
                        amenity.setName(amenityNode.path("description").asText()); // Set the amenity description
                        amenity.setChargeable(amenityNode.path("isChargeable").asBoolean()); // Set if the amenity is chargeable
                        amenity.setSegmentId(fareDetailsNode.path("segmentId").asText()); // Set the segment ID
                        amenity.setTravelerId(travelerNode.path("travelerId").asText()); // Set the traveler ID
                        amenities.add(amenity); // Add the amenity to the list
                    }
                }

                travelerPricings.add(traveler); // Add the traveler to the list
            }

            for (FlightSegment segment : flight.getSegments()) {
                List<TravelerPricing> newTravelerPricings = new ArrayList<>();
                for (TravelerPricing traveler : travelerPricings) {
                    TravelerPricing newTraveler = new TravelerPricing();
                    newTraveler.setId(traveler.getId());
                    newTraveler.setCabin(traveler.getCabin());
                    newTraveler.setFlightClass(traveler.getFlightClass());
                    newTraveler.setTotalPrice(traveler.getTotalPrice());
                    newTraveler.setAmenities(new ArrayList<>()); // Initialize the Amenities list for the traveler
                    newTravelerPricings.add(newTraveler);
                }
                segment.setTravelerPricings(newTravelerPricings);
            }

            if (!amenities.isEmpty()) {
                for (FlightSegment segment : flight.getSegments()) {
                    for (TravelerPricing traveler : segment.getTravelerPricings()) {
                        List<Amenity> travelerAmenities = new ArrayList<>();
                        for (Amenity amenity : amenities) {
                            if (segment.getId().equals(amenity.getSegmentId()) && traveler.getId().equals(amenity.getTravelerId())) {
                                travelerAmenities.add(amenity);
                            }
                        }
                        traveler.setAmenities(travelerAmenities);
                    }
                }
            }



            flights.add(flight); // Add the flight to the list
        }
        return flights; // Return the list of flights
    }
}
