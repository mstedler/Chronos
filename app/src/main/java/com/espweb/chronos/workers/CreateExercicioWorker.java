package com.espweb.chronos.workers;

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
import com.espweb.chronos.workers.base.ApiWorker;

import java.io.IOException;

import retrofit2.Response;

public class CreateExercicioWorker extends ApiWorker {
    public static final String ID_EXERCICIO = "ID_EXERCICIO";

    public CreateExercicioWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public void work() throws IOException, NullPointerException, NotFoundException {
        ExercicioBox exercicioBox = new ExercicioBox();

        long id = getInputData().getLong(ID_EXERCICIO, -1);

        Exercicio exercicio = exercicioBox.get(id);

        Artefato artefato = new Artefato(StorageToNetworkConverter.convert(exercicio));

        ArtefatoService artefatoService = RestClient.createService(ArtefatoService.class);

        Response<Artefato> response = artefatoService.create(artefato).execute();

        exercicio.setUuid(response.body().getResponseExercicio().getUuid());

        exercicioBox.put(exercicio);
    }
}
