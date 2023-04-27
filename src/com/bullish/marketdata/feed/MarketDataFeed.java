package com.bullish.marketdata.feed;

import com.bullish.marketdata.LifeCycle;
import com.bullish.marketdata.common.TickData;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class MarketDataFeed extends LifeCycle implements IMarketDataFeed{

    private BlockingQueue<TickData> feederQueue;
    private HashMap<String, CurrentLowHigh> symbolCurrentTickDataMappings;
    private long timeout;
    private int maxCountToProcess;

    public MarketDataFeed(BlockingQueue<TickData> feederQueue, long timeout, int maxCountToProcess) {
        super();
        this.feederQueue = feederQueue;
        this.timeout = timeout;
        this.symbolCurrentTickDataMappings = new HashMap<>();
        this.maxCountToProcess = maxCountToProcess;
    }

    @Override
    public void process() {
        try{
            TickData data = this.feederQueue.poll(timeout, TimeUnit.MILLISECONDS);
            if(data != null)
            {
                processNewTickData(data);

                this.maxCountToProcess -= 1;
            }
            if(this.maxCountToProcess <= 0)
                stopCycle();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private void processNewTickData(TickData data) {
        CurrentLowHigh currentData = this.symbolCurrentTickDataMappings.getOrDefault(data.getSymbol(), new CurrentLowHigh(0, Double.MAX_VALUE, Double.MIN_VALUE));
        if(data.getPrice() > currentData.getHighPrice())
            currentData.setHighPrice(data.getPrice());
        if(data.getPrice() < currentData.getLowPrice())
            currentData.setLowPrice(data.getPrice());
        currentData.setCurrentPrice(data.getPrice());
        this.symbolCurrentTickDataMappings.put(data.getSymbol(), currentData);
    }

    @Override
    public double getDayLowestPrice(String symbol) {
        return this.symbolCurrentTickDataMappings.getOrDefault(symbol, CurrentLowHigh.EMPTY).getLowPrice();
    }

    @Override
    public double getDayHighestPrice(String symbol) {
        return this.symbolCurrentTickDataMappings.getOrDefault(symbol, CurrentLowHigh.EMPTY).getHighPrice();
    }

    @Override
    public double getCurrentPrice(String symbol) {
        return this.symbolCurrentTickDataMappings.getOrDefault(symbol, CurrentLowHigh.EMPTY).getCurrentPrice();
    }
}
