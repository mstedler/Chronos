package com.espweb.chronos.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.network.RestClient;
import com.espweb.chronos.network.services.ArtefatoService;
import com.espweb.chronos.workers.base.ApiWorker;

import java.io.IOException;

public class DeleteExercicioWorker extends ApiWorker {
    public static final String KEY_UIID_ARTEFATO = "UUID_ARTEFATO";

    public DeleteExercicioWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public void work() throws IOException, NullPointerException, NotFoundException {
        String uuid = getInputData().getString(KEY_UIID_ARTEFATO);
        ArtefatoService artefatoService = RestClient.createService(ArtefatoService.class);
        artefatoService.deleteExercicio(uuid).execute();
    }
}
