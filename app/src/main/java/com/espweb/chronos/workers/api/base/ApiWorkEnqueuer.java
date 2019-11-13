package com.espweb.chronos.workers.api.base;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class ApiWorkEnqueuer {
    public static void enqueueUnique(Context context, @NonNull OneTimeWorkRequest workRequest) {
        WorkManager.getInstance(context).enqueueUniqueWork(ApiWorker.NAME, ExistingWorkPolicy.APPEND, workRequest);
    }
}
