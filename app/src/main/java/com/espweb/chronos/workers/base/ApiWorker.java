package com.espweb.chronos.workers.base;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.espweb.chronos.domain.exceptions.NotFoundException;

import java.io.IOException;

public abstract class WebRequestWorker extends Worker {
    public static String NAME = "WEB_REQUEST";

    public WebRequestWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    public abstract void work() throws IOException, NullPointerException, NotFoundException;


    @NonNull
    @Override
    public Result doWork() {
        try {
            work();
            return Result.success();
        } catch (IOException e) {
            Log.e(NAME, e.getMessage());
            return Result.retry();
        } catch (NullPointerException | NotFoundException e) {
            Log.e(NAME, e.getMessage());
            return Result.success();
        } catch (Exception e) {
            WorkManager.getInstance(getApplicationContext()).cancelUniqueWork(NAME);
            WorkManager.getInstance(getApplicationContext()).pruneWork();
            return Result.success();
        }
    }
}
