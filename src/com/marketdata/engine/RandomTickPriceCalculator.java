package com.marketdata.engine;

import java.util.Random;

public class RandomTickPriceCalculator implements ITickPriceCalculator {

    private final Random random;
    public RandomTickPriceCalculator() {
        this.random = new Random();
    }

    @Override
    public double getTickPrice(int tickNumber, double currentPrice) {
        double randomVariance = random.nextInt(10) + 1;
        return tickNumber % 2 == 0? currentPrice - randomVariance: currentPrice + randomVariance;
    }
}
