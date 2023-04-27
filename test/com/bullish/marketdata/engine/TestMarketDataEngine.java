package com.bullish.marketdata.engine;

import com.bullish.marketdata.ILifeCycle;
import com.bullish.marketdata.common.TickData;
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
        ArrayBlockingQueue<TickData> queue = new ArrayBlockingQueue<TickData>(1000);
        IMarketDataEngine marketDataEngine = new MarketDataEngine("TEST", 20000,10, queue, 10);
        ((ILifeCycle)marketDataEngine).process();

        Assert.assertEquals(10, queue.size());

        Assert.assertEquals(20100, queue.toArray(new TickData[10])[9].getPrice(), 0.0001);

    }


    @Test
    public void TestRandomizeTickPricePublication()
    {
        RandomTickSizeCalculator randomTickSizeCalculator =  new RandomTickSizeCalculator();
        Assert.assertEquals(true, randomTickSizeCalculator.getPrice(0, 10) < 10);
        Assert.assertEquals(true, randomTickSizeCalculator.getPrice(1, 10) > 10);
    }
}
