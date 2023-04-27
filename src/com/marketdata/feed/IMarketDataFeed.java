package com.marketdata.feed;

public interface IMarketDataFeed {
    double getLowestPrice(String symbol);
    double getHighestPrice(String symbol);
    double getCurrentPrice(String symbol);
}
