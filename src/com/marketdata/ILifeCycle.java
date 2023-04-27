package com.marketdata;

public interface ILifeCycle {
    void startProcess();
    void stopProcess();
    void process();
    boolean isStopped();
}
