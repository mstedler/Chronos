package com.espweb.chronos;

import android.app.Application;

import com.espweb.chronos.data.SessaoRepositoryImpl;
import com.espweb.chronos.domain.repository.SessaoRepository;
import com.espweb.chronos.network.RestClient;
import com.espweb.chronos.storage.database.ObjectBox;
import com.facebook.stetho.Stetho;
import com.jakewharton.threetenabp.AndroidThreeTen;

public class CronogramaApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ObjectBox.init(this);
        RestClient.init(new SessaoRepositoryImpl(this));
        AndroidThreeTen.init(this);

        Stetho.initializeWithDefaults(this);
    }
}
