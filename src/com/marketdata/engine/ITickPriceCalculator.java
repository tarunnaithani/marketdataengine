package com.marketdata.engine;

public interface ITickPriceCalculator {
    double getTickPrice(int tickNumber, double price);
}
