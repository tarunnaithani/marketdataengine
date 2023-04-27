package com.marketdata.engine;

import com.marketdata.LifeCycle;
import com.marketdata.common.TickData;

import java.util.concurrent.BlockingQueue;

public class MarketDataEngine extends LifeCycle implements IMarketDataEngine{

    private TickData currentTickData;
    private final BlockingQueue<TickData> publisherQueue;
    private final int maxPublishCount;

    private final ITickPriceCalculator tickPriceCalculator;

    public MarketDataEngine(String symbol, double initialPrice, double tickSize, BlockingQueue<TickData> feederQueue, int maxPublishCount)
    {
        this(symbol, initialPrice, feederQueue, maxPublishCount, new StaticTickPriceCalculator(tickSize));
    }

    public MarketDataEngine(String symbol, double initialPrice, BlockingQueue<TickData> publisherQueue, int maxPublishCount, ITickPriceCalculator tickPriceCalculator)
    {
        super();
        this.currentTickData = new TickData(symbol, initialPrice);
        this.publisherQueue = publisherQueue;
        this.maxPublishCount = maxPublishCount;
        this.tickPriceCalculator = tickPriceCalculator;
    }

    @Override
    public void publishTicks(int tickNumber) {
        try {
            TickData newData = new TickData(this.currentTickData.getSymbol(), this.tickPriceCalculator.getTickPrice(tickNumber, this.currentTickData.getPrice()));
            publisherQueue.put(newData);
            this.currentTickData = newData;
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void process() {
        for(int tickNumber =0; tickNumber < maxPublishCount; tickNumber++)
            publishTicks(tickNumber);
        stopProcess();
    }
}
