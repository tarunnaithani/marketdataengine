package com.marketdata;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class LifeCycle extends Thread implements ILifeCycle{

    private final AtomicBoolean stop;

    public LifeCycle() {
        this.stop = new AtomicBoolean(false);
    }

    @Override
    public void startProcess() {
        this.stop.set(false);
        this.start();
    }

    @Override
    public void stopProcess() {
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
