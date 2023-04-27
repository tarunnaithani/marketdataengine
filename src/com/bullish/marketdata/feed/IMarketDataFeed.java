package com.bullish.marketdata.feed;

public interface IMarketDataFeed {
    double getDayLowestPrice(String symbol);
    double getDayHighestPrice(String symbol);
    double getCurrentPrice(String symbol);
}
