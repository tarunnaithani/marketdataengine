package com.bullish.marketdata.feed;

public class CurrentLowHigh {

    public static CurrentLowHigh EMPTY = new CurrentLowHigh(0, Double.MAX_VALUE, Double.MIN_VALUE);
    private double currentPrice;
    private double lowPrice;
    private double highPrice;

    public CurrentLowHigh(double currentPrice, double lowPrice, double highPrice) {
        this.currentPrice = currentPrice;
        this.lowPrice = lowPrice;
        this.highPrice = highPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }
}
