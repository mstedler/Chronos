package com.espweb.chronos.workers.base;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.espweb.chronos.workers.exceptions.FailureException;
import com.espweb.chronos.workers.exceptions.RetryException;

public abstract class AbstractWorker extends Worker {
    public static String NAME = "WEB_REQUEST";

    public AbstractWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    public abstract void work() throws RetryException, FailureException;


    @NonNull
    @Override
    public Result doWork() {
        try {
            work();
            return Result.success();
        } catch (RetryException e) {
            return Result.retry();
        } catch (FailureException e) {
            WorkManager.getInstance(getApplicationContext()).cancelUniqueWork(NAME);
            WorkManager.getInstance(getApplicationContext()).pruneWork();
            return Result.failure();
        }
    }
}
