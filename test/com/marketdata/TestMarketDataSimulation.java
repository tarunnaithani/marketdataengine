package com.marketdata;

import com.marketdata.common.TickData;
import com.marketdata.engine.MarketDataEngine;
import com.marketdata.engine.RandomTickPriceCalculator;
import com.marketdata.feed.MarketDataFeed;
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
        ArrayBlockingQueue<TickData> queue = new ArrayBlockingQueue<>(1000);
        MarketDataFeed marketDataFeed = new MarketDataFeed(queue, 10, 10);
        MarketDataEngine marketDataEngine = new MarketDataEngine(SYMBOL, 20000,10, queue, 10);
        marketDataEngine.startProcess();
        marketDataFeed.startProcess();
        while(!marketDataFeed.isStopped() )
            Thread.sleep(1);
        Assert.assertEquals(20100, marketDataFeed.getCurrentPrice(SYMBOL), 0.0001);
        Assert.assertEquals(20100, marketDataFeed.getHighestPrice(SYMBOL), 0.0001);
        Assert.assertEquals(20010, marketDataFeed.getLowestPrice(SYMBOL), 0.0001);

    }

    @Test public void TestMultipleTickDataFeedCollection() throws InterruptedException
    {
        ArrayBlockingQueue<TickData> queue = new ArrayBlockingQueue<>(1000);
        MarketDataFeed marketDataFeed = new MarketDataFeed(queue, 10, 20);
        String btcusd = "BTCUSD";
        MarketDataEngine marketDataEngineBTC = new MarketDataEngine(btcusd, 20000,10, queue, 10);
        marketDataEngineBTC.startProcess();

        String ethusd = "ETHUSD";
        MarketDataEngine marketDataEngineETH = new MarketDataEngine(ethusd, 1000,10, queue, 10);
        marketDataEngineETH.startProcess();

        marketDataFeed.startProcess();
        while(!marketDataFeed.isStopped() )
            Thread.sleep(1);

        Assert.assertEquals(20100, marketDataFeed.getCurrentPrice(btcusd), 0.0001);
        Assert.assertEquals(20100, marketDataFeed.getHighestPrice(btcusd), 0.0001);
        Assert.assertEquals(20010, marketDataFeed.getLowestPrice(btcusd), 0.0001);

        Assert.assertEquals(1100, marketDataFeed.getCurrentPrice(ethusd), 0.0001);
        Assert.assertEquals(1100, marketDataFeed.getHighestPrice(ethusd), 0.0001);
        Assert.assertEquals(1010, marketDataFeed.getLowestPrice(ethusd), 0.0001);

    }

    @Test public void TestTickDataFeedCollectionWithRandomTicks() throws InterruptedException
    {
        ArrayBlockingQueue<TickData> queue = new ArrayBlockingQueue<>(1000);
        MarketDataFeed marketDataFeed = new MarketDataFeed(queue, 10, 10);
        MarketDataEngine marketDataEngine = new MarketDataEngine(SYMBOL, 20000,queue, 10, new RandomTickPriceCalculator());
        marketDataEngine.startProcess();
        marketDataFeed.startProcess();
        while(!marketDataFeed.isStopped() )
            Thread.sleep(1);
        Assert.assertTrue(marketDataEngine.isStopped());

    }



}
