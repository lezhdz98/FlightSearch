package com.encora.flightsearchBT.models;


import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class FlightRequest {

    @NotEmpty(message = "Origin is required")
    private String origin;

    @NotEmpty(message = "Destination is required")
    private String destination;

    @NotNull(message = "Departure date is required")
    @FutureOrPresent(message = "Departure date cannot be in the past")
    private LocalDate departureDate;

    private LocalDate returnDate;

    @NotNull(message = "Number of adults is required")
    private Integer adults;

    private boolean nonStop;
    private String currencyCode;

    public @NotEmpty(message = "Origin is required") String getOrigin() {
        return origin;
    }

    public void setOrigin(@NotEmpty(message = "Origin is required") String origin) {
        this.origin = origin;
    }

    public @NotEmpty(message = "Destination is required") String getDestination() {
        return destination;
    }

    public void setDestination(@NotEmpty(message = "Destination is required") String destination) {
        this.destination = destination;
    }

    public @NotNull(message = "Departure date is required") @FutureOrPresent(message = "Departure date cannot be in the past") LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(@NotNull(message = "Departure date is required") @FutureOrPresent(message = "Departure date cannot be in the past") LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public @NotNull(message = "Number of adults is required") Integer getAdults() {
        return adults;
    }

    public void setAdults(@NotNull(message = "Number of adults is required") Integer adults) {
        this.adults = adults;
    }

    public boolean isNonStop() {
        return nonStop;
    }

    public void setNonStop(boolean nonStop) {
        this.nonStop = nonStop;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
