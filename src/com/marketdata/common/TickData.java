package com.marketdata.common;

public class TickData {
    private final String symbol;
    private final double price;

    public TickData(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }
}
