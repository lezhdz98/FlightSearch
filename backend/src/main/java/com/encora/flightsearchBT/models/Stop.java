package com.encora.flightsearchBT.models;

import java.time.Duration;

public class Stop {
    private Airport airport;
    private Duration duration;

    // Getters and Setters
    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}

