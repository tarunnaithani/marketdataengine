package com.bullish.marketdata;

import com.bullish.marketdata.common.TickData;
import com.bullish.marketdata.engine.MarketDataEngine;
import com.bullish.marketdata.engine.RandomTickSizeCalculator;
import com.bullish.marketdata.feed.MarketDataFeed;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.ArrayBlockingQueue;

@RunWith(JUnit4.class)
public class TestMarketDataSimulation {

    public static final String SYMBOL = "TEST";

    @Test public void TestTickDataFeedCollection() throws InterruptedException
    {
        ArrayBlockingQueue<TickData> queue = new ArrayBlockingQueue<TickData>(1000);
        MarketDataFeed marketDataFeed = new MarketDataFeed(queue, 10, 10);
        MarketDataEngine marketDataEngine = new MarketDataEngine(SYMBOL, 20000,10, queue, 10);
        marketDataEngine.startCycle();
        marketDataFeed.startCycle();
        while(!marketDataFeed.isStopped() )
            Thread.sleep(1);
        Assert.assertEquals(20100, marketDataFeed.getCurrentPrice(SYMBOL), 0.0001);
        Assert.assertEquals(20100, marketDataFeed.getDayHighestPrice(SYMBOL), 0.0001);
        Assert.assertEquals(20010, marketDataFeed.getDayLowestPrice(SYMBOL), 0.0001);

    }

    @Test public void TestMultipleTickDataFeedCollection() throws InterruptedException
    {
        ArrayBlockingQueue<TickData> queue = new ArrayBlockingQueue<TickData>(1000);
        MarketDataFeed marketDataFeed = new MarketDataFeed(queue, 10, 20);
        String btcusd = "BTCUSD";
        MarketDataEngine marketDataEngineBTC = new MarketDataEngine(btcusd, 20000,10, queue, 10);
        marketDataEngineBTC.startCycle();

        String ethusd = "ETHUSD";
        MarketDataEngine marketDataEngineETH = new MarketDataEngine(ethusd, 1000,10, queue, 10);
        marketDataEngineETH.startCycle();

        marketDataFeed.startCycle();
        while(!marketDataFeed.isStopped() )
            Thread.sleep(1);

        Assert.assertEquals(20100, marketDataFeed.getCurrentPrice(btcusd), 0.0001);
        Assert.assertEquals(20100, marketDataFeed.getDayHighestPrice(btcusd), 0.0001);
        Assert.assertEquals(20010, marketDataFeed.getDayLowestPrice(btcusd), 0.0001);

        Assert.assertEquals(1100, marketDataFeed.getCurrentPrice(ethusd), 0.0001);
        Assert.assertEquals(1100, marketDataFeed.getDayHighestPrice(ethusd), 0.0001);
        Assert.assertEquals(1010, marketDataFeed.getDayLowestPrice(ethusd), 0.0001);

    }

    @Test public void TestTickDataFeedCollectionWithRandomTicks() throws InterruptedException
    {
        ArrayBlockingQueue<TickData> queue = new ArrayBlockingQueue<TickData>(1000);
        MarketDataFeed marketDataFeed = new MarketDataFeed(queue, 10, 10);
        MarketDataEngine marketDataEngine = new MarketDataEngine(SYMBOL, 20000,queue, 10, new RandomTickSizeCalculator());
        marketDataEngine.startCycle();
        marketDataFeed.startCycle();
        while(!marketDataFeed.isStopped() )
            Thread.sleep(1);
        Assert.assertEquals(true, marketDataEngine.isStopped());

    }



}
