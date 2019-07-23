package com.espweb.chronos;

import android.app.Application;

import com.espweb.chronos.data.SessaoRepositoryImpl;
import com.espweb.chronos.network.RestClient;
import com.espweb.chronos.storage.database.ObjectBox;
import com.jakewharton.threetenabp.AndroidThreeTen;

public class CronogramaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RestClient.init(new SessaoRepositoryImpl(this));
        ObjectBox.init(this);
        AndroidThreeTen.init(this);
    }
}
