package com.espweb.chronos.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.network.RestClient;
import com.espweb.chronos.network.services.AssuntoService;
import com.espweb.chronos.storage.boxes.AssuntoBox;
import com.espweb.chronos.storage.model.Assunto;
import com.espweb.chronos.workers.base.WebRequestWorker;

import java.io.IOException;


public class DeleteAssuntoWorker extends WebRequestWorker {

    public static String KEY_UUID_ASSUNTO = "UUID_ASSUNTO";

    public DeleteAssuntoWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public void work() throws IOException, NullPointerException, NotFoundException {
        String uuid = getInputData().getString(KEY_UUID_ASSUNTO);
        AssuntoService assuntoService = RestClient.createService(AssuntoService.class);
        assuntoService.delete(uuid).execute();
    }
}
