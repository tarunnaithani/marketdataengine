package com.marketdata.engine;

import com.marketdata.common.TickData;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.ArrayBlockingQueue;

@RunWith(JUnit4.class)
public class TestMarketDataEngine {

    @Test
    public void TestTickDataPublication()
    {
        ArrayBlockingQueue<TickData> queue = new ArrayBlockingQueue<>(1000);
        MarketDataEngine marketDataEngine = new MarketDataEngine("TEST", 20000,10, queue, 10);
        marketDataEngine.process();

        Assert.assertEquals(10, queue.size());
        Assert.assertEquals(20100, queue.toArray(new TickData[10])[9].getPrice(), 0.0001);
    }


    @Test
    public void TestRandomizeTickPricePublication()
    {
        RandomTickPriceCalculator randomTickSizeCalculator =  new RandomTickPriceCalculator();
        Assert.assertTrue(randomTickSizeCalculator.getTickPrice(0, 10) < 10);
        Assert.assertTrue(randomTickSizeCalculator.getTickPrice(1, 10) > 10);
    }
}
