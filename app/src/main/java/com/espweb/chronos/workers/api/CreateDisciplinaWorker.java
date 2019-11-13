package com.espweb.chronos.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.network.RestClient;
import com.espweb.chronos.network.converters.DateConverter;
import com.espweb.chronos.network.services.DisciplinaService;
import com.espweb.chronos.storage.boxes.DisciplinaBox;
import com.espweb.chronos.storage.model.Cronograma;
import com.espweb.chronos.storage.model.Disciplina;
import com.espweb.chronos.workers.base.ApiWorker;

import java.io.IOException;

import retrofit2.Response;

public class CreateDisciplinaWorker extends ApiWorker {
    public static final String KEY_ID_DISCIPLINA = "ID_DISCIPLINA";

    public CreateDisciplinaWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public void work() throws IOException, NullPointerException, NotFoundException {
        DisciplinaBox disciplinaBox = new DisciplinaBox();
        long id = getInputData().getLong(KEY_ID_DISCIPLINA, 0);
        Disciplina disciplina = disciplinaBox.get(id);
        Cronograma cronograma = disciplina.getCronograma().getTarget();
        DisciplinaService disciplinaService = RestClient.createService(DisciplinaService.class);
        Response<com.espweb.chronos.network.model.Disciplina> response = disciplinaService.create(
                disciplina.getNome(),
                disciplina.getDescricao(),
                cronograma.getUuid(),
                DateConverter.format(cronograma.getFim())).execute();

        com.espweb.chronos.network.model.Disciplina nDisciplina = response.body().getDisciplina();
        disciplina.setUuid(nDisciplina.getUuid());
        disciplinaBox.put(disciplina);
    }
}
