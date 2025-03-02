package com.example.mockito;

import java.math.BigDecimal;

public class SalesSummary {
    private int soldItemsCount;
    private BigDecimal totalRevenue;

    public SalesSummary(int soldItemsCount, BigDecimal totalRevenue) {
        this.soldItemsCount = soldItemsCount;
        this.totalRevenue = totalRevenue;
    }

    public int getSoldItemsCount() {
        return soldItemsCount;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }
}
