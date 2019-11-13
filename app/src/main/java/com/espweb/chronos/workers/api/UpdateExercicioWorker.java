package com.espweb.chronos.workers.api;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.network.RestClient;
import com.espweb.chronos.network.model.Artefato;
import com.espweb.chronos.network.services.ArtefatoService;
import com.espweb.chronos.storage.boxes.ExercicioBox;
import com.espweb.chronos.storage.converters.StorageToNetworkConverter;
import com.espweb.chronos.storage.model.Exercicio;
import com.espweb.chronos.workers.api.base.ApiWorker;

import java.io.IOException;

public class UpdateExercicioWorker extends ApiWorker {

    public static String KEY_ID_EXERCICIO = "KEY_ID_EXERCICIO";

    public UpdateExercicioWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }


    @Override
    public void work() throws IOException, NullPointerException, NotFoundException {
        long id = getInputData().getLong(KEY_ID_EXERCICIO, 0);
        ExercicioBox exercicioBox = new ExercicioBox();

        Exercicio exercicio = exercicioBox.get(id);
        Artefato artefato = new Artefato();
        artefato.setResponseExercicio(StorageToNetworkConverter.convert(exercicio));

        ArtefatoService artefatoService = RestClient.createService(ArtefatoService.class);
        artefatoService.update(artefato, exercicio.getUuid()).execute();
    }
}
