package com.marketdata.engine;

public class StaticTickPriceCalculator implements ITickPriceCalculator {
    private final double tickSize;

    public StaticTickPriceCalculator(double tickSize) {
        this.tickSize = tickSize;
    }

    @Override
    public double getTickPrice(int tickNumber, double price) {
        return price + this.tickSize;
    }
}
