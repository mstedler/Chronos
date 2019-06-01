package com.espweb.chronos.threading;

import com.espweb.chronos.domain.executor.MainThread;

public class TestMainThread implements MainThread {
    @Override
    public void post(Runnable runnable) {
        runnable.run();
    }
}
