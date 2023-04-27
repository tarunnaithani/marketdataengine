package com.bullish.marketdata.common;

public class TickData {
    private String  symbol;
    private double price;

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
