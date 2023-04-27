package com.marketdata.feed;

import com.marketdata.common.TickData;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.ArrayBlockingQueue;

@RunWith(JUnit4.class)
public class TestMarketDataFeed {

    public static final String SYMBOL = "TEST";

    @Test
    public void TestTickDataFeedCollection() throws InterruptedException {
        ArrayBlockingQueue<TickData> queue = new ArrayBlockingQueue<>(1000);
        MarketDataFeed marketDataFeed = new MarketDataFeed(queue, 10, 10);

        for (int i = 0; i < 10; i++) {
            double price = 20000 + (10 * i);
            queue.put(new TickData(SYMBOL, price));
            marketDataFeed.process();
            Assert.assertEquals(price, marketDataFeed.getCurrentPrice(SYMBOL), 0.0001);
            Assert.assertEquals(price, marketDataFeed.getHighestPrice(SYMBOL), 0.0001);
            Assert.assertEquals(20000, marketDataFeed.getLowestPrice(SYMBOL), 0.0001);
        }
        double price = 19000;
        queue.put(new TickData("TEST", 19000));
        marketDataFeed.process();
        Assert.assertEquals(price, marketDataFeed.getCurrentPrice(SYMBOL), 0.0001);
        Assert.assertEquals(20090, marketDataFeed.getHighestPrice(SYMBOL), 0.0001);
        Assert.assertEquals(19000, marketDataFeed.getLowestPrice(SYMBOL), 0.0001);
    }
}
