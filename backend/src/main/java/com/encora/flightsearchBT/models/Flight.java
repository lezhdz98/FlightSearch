package com.encora.flightsearchBT.models;

import java.time.Duration;
import java.util.List;

public class Flight {
    private String id;
    private Duration totalFlightTime;
    private List<FlightSegment> segments;
    private PriceBreakdown priceBreakdown;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Duration getTotalFlightTime() {
        return totalFlightTime;
    }

    public void setTotalFlightTime(Duration totalFlightTime) {
        this.totalFlightTime = totalFlightTime;
    }

    public List<FlightSegment> getSegments() {
        return segments;
    }

    public void setSegments(List<FlightSegment> segments) {
        this.segments = segments;
    }

    public PriceBreakdown getPriceBreakdown() {
        return priceBreakdown;
    }

    public void setPriceBreakdown(PriceBreakdown priceBreakdown) {
        this.priceBreakdown = priceBreakdown;
    }
}
