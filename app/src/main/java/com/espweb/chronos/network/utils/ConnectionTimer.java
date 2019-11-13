package com.espweb.chronos.network.utils;

import com.espweb.chronos.domain.executor.MainThread;

import java.util.Timer;
import java.util.TimerTask;

public class ConnectionTimer {

    public interface ConnectionTimerListener {
        void isConnected();

        void isDisconnected();
    }

    private ConnectionTimerListener listener;
    private MainThread mainThread;

    private Timer timer;

    public ConnectionTimer(ConnectionTimerListener listener, MainThread mainThread) {
        this.listener = listener;
        this.mainThread = mainThread;
    }

    public void startChecking() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                boolean isOnline = Connection.isOnline();
                if (isOnline) {
                    mainThread.post(() -> listener.isConnected());
                } else {
                    mainThread.post(() -> listener.isDisconnected());
                }
            }
        }, 2000, 2000);
    }

    public void stopChecking() {
        timer.cancel();
    }

}
