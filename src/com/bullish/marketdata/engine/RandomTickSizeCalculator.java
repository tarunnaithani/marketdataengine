package com.bullish.marketdata.engine;

import java.util.Random;

public class RandomTickSizeCalculator implements ITickPriceCalculator {

    private Random random;
    public RandomTickSizeCalculator() {
        this.random = new Random();
    }

    @Override
    public double getPrice(int i, double price) {
        double variance = random.nextInt(10) + 1;
        double finalPrice = i % 2 == 0? price - variance: price + variance;
        return finalPrice;
    }
}
