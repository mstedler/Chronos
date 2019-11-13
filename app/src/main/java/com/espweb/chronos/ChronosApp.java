package com.espweb.chronos;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.espweb.chronos.data.SessaoRepositoryImpl;
import com.espweb.chronos.network.RestClient;
import com.espweb.chronos.storage.database.ObjectBox;
import com.facebook.stetho.Stetho;
import com.jakewharton.threetenabp.AndroidThreeTen;

public class ChronosApp extends Application {
    public static final String CHANNEL_ID = "CHRONOS_CHANNEL_ID";

    @Override
    public void onCreate() {
        super.onCreate();
        ObjectBox.init(this);
        RestClient.init(new SessaoRepositoryImpl(this));
        AndroidThreeTen.init(this);
        Stetho.initializeWithDefaults(this);
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
