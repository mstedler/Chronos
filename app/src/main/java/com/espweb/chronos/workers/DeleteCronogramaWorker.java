package com.espweb.chronos.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.network.RestClient;
import com.espweb.chronos.network.services.CronogramaService;
import com.espweb.chronos.workers.base.ApiWorker;

import java.io.IOException;

public class DeleteCronogramaWorker extends ApiWorker {

    public static final String KEY_UUID_CRONOGRAMA = "UUID_CRONOGRAMA";

    public DeleteCronogramaWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public void work() throws IOException, NullPointerException, NotFoundException {
        String uuid = getInputData().getString(KEY_UUID_CRONOGRAMA);
        CronogramaService cronogramaService = RestClient.createService(CronogramaService.class);
        cronogramaService.delete(uuid).execute();
    }
}
