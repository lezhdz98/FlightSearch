package com.encora.flightsearchBT.models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class FlightSegment {
    private String id;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private Airline airline;
    private Airline operatingAirline; // Optional
    private Duration layoverTime; 
    private List<Stop> stops;
    private String flightNumber;
    private String aircraftType;
    private List<TravelerPricing> travelerPricings;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(LocalDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public Airline getOperatingAirline() {
        return operatingAirline;
    }

    public void setOperatingAirline(Airline operatingAirline) {
        this.operatingAirline = operatingAirline;
    }

    public Duration getLayoverTime() {
        return layoverTime;
    }

    public void setLayoverTime(Duration layoverTime) {
        this.layoverTime = layoverTime;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }

    public List<TravelerPricing> getTravelerPricings() {
        return travelerPricings;
    }

    public void setTravelerPricings(List<TravelerPricing> travelerPricings) {
        this.travelerPricings = travelerPricings;
    }

}
