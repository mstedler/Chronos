package com.espweb.chronos.workers.base;

import android.content.Context;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.ListenableWorker;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;


public class WorkFactory {

    public static void enqueue(Context context, Data inputData, Class<? extends ListenableWorker> c) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(c)
                .setConstraints(constraints)
                .setInputData(inputData)
                .build();

        WorkManager.getInstance(context).enqueueUniqueWork(WebRequestWorker.NAME, ExistingWorkPolicy.APPEND, workRequest);
    }
}
