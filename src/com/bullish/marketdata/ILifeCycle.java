package com.bullish.marketdata;

public interface ILifeCycle {
    void startCycle();
    void stopCycle();
    void process();
    boolean isStopped();
}
