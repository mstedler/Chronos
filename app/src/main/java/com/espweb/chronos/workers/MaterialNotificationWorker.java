package com.espweb.chronos.workers;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.espweb.chronos.ChronosApp;
import com.espweb.chronos.R;

public class MaterialNotificationWorker extends Worker {
    public MaterialNotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context context = getApplicationContext();
        Data data = getInputData();
        int escopo = data.getInt("escopo", 0);

        String escopoS = context.getResources().getStringArray(R.array.materials)[escopo - 1];


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, ChronosApp.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle(escopoS)
                .setContentText("Sua atividade foi concluída!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(1, builder.build());

        return Result.success();
    }
}
