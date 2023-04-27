package com.bullish.marketdata;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class LifeCycle extends Thread implements ILifeCycle{

    private AtomicBoolean stop;

    public LifeCycle() {
        this.stop = new AtomicBoolean(false);
    }

    @Override
    public void startCycle() {
        this.stop.set(false);
        this.start();
    }

    @Override
    public void stopCycle() {
        this.stop.set(true);
    }

    @Override
    public void run() {
        while(!stop.get())
            process();
    }

    @Override
    public boolean isStopped() {
        return this.stop.get();
    }
}
