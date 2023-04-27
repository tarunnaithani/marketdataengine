package com.bullish.marketdata.engine;

public class StaticTickSizeCalculator  implements ITickPriceCalculator {
    private double tickSize;

    public StaticTickSizeCalculator(double tickSize) {
        this.tickSize = tickSize;
    }

    @Override
    public double getPrice(int i, double price) {
        return price + this.tickSize;
    }
}
