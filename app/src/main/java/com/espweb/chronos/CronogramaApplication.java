package com.espweb.chronos;

import android.app.Application;

import com.espweb.chronos.storage.database.ObjectBox;

public class CronogramaApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ObjectBox.init(this);
    }
}
