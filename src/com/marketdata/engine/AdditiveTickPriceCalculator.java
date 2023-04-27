package com.marketdata.engine;

public class AdditiveTickPriceCalculator implements ITickPriceCalculator {
    private final double tickSize;

    public AdditiveTickPriceCalculator(double tickSize) {
        this.tickSize = tickSize;
    }

    @Override
    public double getTickPrice(int tickNumber, double price) {
        return price + this.tickSize;
    }
}
