package com.encora.flightsearchBT.models;

import java.math.BigDecimal;
import java.util.List;

public class PriceBreakdown {
    private BigDecimal basePrice;
    private BigDecimal totalPrice;
    private BigDecimal fees;

    // Getters and Setters
    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getFees() {
        return fees;
    }

    public void setFees(BigDecimal fees) {
        this.fees = fees;
    }

}
