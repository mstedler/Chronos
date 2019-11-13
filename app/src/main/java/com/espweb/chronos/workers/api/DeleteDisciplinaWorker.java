package com.espweb.chronos.workers.api;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.network.RestClient;
import com.espweb.chronos.network.services.DisciplinaService;
import com.espweb.chronos.workers.api.base.ApiWorker;

import java.io.IOException;

public class DeleteDisciplinaWorker extends ApiWorker {
    public static final String KEY_UUID_DISCIPLINA = "UUID_DISCIPLINA";

    public DeleteDisciplinaWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public void work() throws IOException, NullPointerException, NotFoundException {
        String uuid = getInputData().getString(KEY_UUID_DISCIPLINA);
        DisciplinaService disciplinaService = RestClient.createService(DisciplinaService.class);
        disciplinaService.delete(uuid).execute();
    }
}
