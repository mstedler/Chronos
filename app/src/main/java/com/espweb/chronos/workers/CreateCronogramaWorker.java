package com.espweb.chronos.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.espweb.chronos.data.CronogramaRepositoryImpl;
import com.espweb.chronos.network.model.Cronograma;

public class CreateCronogramaWorker extends Worker {

    public CreateCronogramaWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context context = getApplicationContext();
        CronogramaRepositoryImpl cronogramaRepository = new CronogramaRepositoryImpl(context);
        try {
            Cronograma cronograma = cronogramaRepository.get(1);


            return Result.success();
        }catch (Exception e){
            return Result.retry();
        }
    }
}
