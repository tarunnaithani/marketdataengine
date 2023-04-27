package com.bullish.marketdata.engine;

import com.bullish.marketdata.LifeCycle;
import com.bullish.marketdata.common.TickData;

import java.util.concurrent.BlockingQueue;

public class MarketDataEngine extends LifeCycle implements IMarketDataEngine{

    private TickData tickData;
    private BlockingQueue<TickData> feederQueue;
    private int maxPublishCount;

    private ITickPriceCalculator tickSizeCalculator;

    public MarketDataEngine(String symbol, double initialPrice, double tickSize, BlockingQueue<TickData> feederQueue, int maxPublishCount)
    {
        this(symbol, initialPrice, feederQueue, maxPublishCount, new StaticTickSizeCalculator(tickSize));
    }

    public MarketDataEngine(String symbol, double initialPrice, BlockingQueue<TickData> feederQueue, int maxPublishCount, ITickPriceCalculator tickSizeCalculator)
    {
        super();
        this.tickData = new TickData(symbol, initialPrice);
        this.feederQueue = feederQueue;
        this.maxPublishCount = maxPublishCount;
        this.tickSizeCalculator = tickSizeCalculator;
    }

    @Override
    public void publishTicks(int i) {
        try {
            TickData newData = new TickData(this.tickData.getSymbol(), this.tickSizeCalculator.getPrice(i, this.tickData.getPrice()));
            feederQueue.put(newData);
            this.tickData = newData;
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void process() {
        for(int i =0; i < maxPublishCount; i++)
            publishTicks(i);
        stopCycle();
    }
}
