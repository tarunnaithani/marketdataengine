package com.marketdata.feed;

import com.marketdata.LifeCycle;
import com.marketdata.common.TickData;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class MarketDataFeed extends LifeCycle implements IMarketDataFeed{

    private final BlockingQueue<TickData> receiverQueue;
    private final HashMap<String, CurrentLowHigh> symbolTickDataMappings;
    private final long timeout;
    private int maxTickDataToConsume;

    public MarketDataFeed(BlockingQueue<TickData> feederQueue, long timeout, int maxTickDataToConsume) {
        super();
        this.receiverQueue = feederQueue;
        this.timeout = timeout;
        this.symbolTickDataMappings = new HashMap<>();
        this.maxTickDataToConsume = maxTickDataToConsume;
    }

    @Override
    public void process() {
        try{
            TickData data = this.receiverQueue.poll(timeout, TimeUnit.MILLISECONDS);
            if(data != null)
            {
                processNewTickData(data);

                this.maxTickDataToConsume -= 1;
            }
            if(this.maxTickDataToConsume <= 0)
                stopProcess();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private void processNewTickData(TickData data) {
        CurrentLowHigh currentData = this.symbolTickDataMappings.getOrDefault(data.getSymbol(), new CurrentLowHigh(0, Double.MAX_VALUE, Double.MIN_VALUE));
        if(data.getPrice() > currentData.getHighPrice())
            currentData.setHighPrice(data.getPrice());
        if(data.getPrice() < currentData.getLowPrice())
            currentData.setLowPrice(data.getPrice());
        currentData.setCurrentPrice(data.getPrice());
        this.symbolTickDataMappings.put(data.getSymbol(), currentData);
    }

    @Override
    public double getLowestPrice(String symbol) {
        return this.symbolTickDataMappings.getOrDefault(symbol, CurrentLowHigh.EMPTY).getLowPrice();
    }

    @Override
    public double getHighestPrice(String symbol) {
        return this.symbolTickDataMappings.getOrDefault(symbol, CurrentLowHigh.EMPTY).getHighPrice();
    }

    @Override
    public double getCurrentPrice(String symbol) {
        return this.symbolTickDataMappings.getOrDefault(symbol, CurrentLowHigh.EMPTY).getCurrentPrice();
    }
}
