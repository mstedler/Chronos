package com.espweb.chronos.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.network.RestClient;
import com.espweb.chronos.network.services.AssuntoService;
import com.espweb.chronos.storage.boxes.AssuntoBox;
import com.espweb.chronos.storage.model.Assunto;
import com.espweb.chronos.storage.model.Disciplina;
import com.espweb.chronos.workers.base.WebRequestWorker;


import java.io.IOException;

import retrofit2.Response;

public class CreateAssuntoWorker extends WebRequestWorker {
    public static final String KEY_ID_ASSUNTO = "ID_ASSUNTO";

    public CreateAssuntoWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public void work() throws IOException, NotFoundException, NullPointerException {
        AssuntoBox assuntoBox = new AssuntoBox();
        long id = getInputData().getLong(CreateAssuntoWorker.KEY_ID_ASSUNTO, 0);
        Assunto assunto = assuntoBox.get(id);
        Disciplina disciplina = assunto.getDisciplina().getTarget();
        AssuntoService assuntoService = RestClient.createService(AssuntoService.class);
        Response<com.espweb.chronos.network.model.Assunto> response = assuntoService.create(assunto.getDescricao(), "wtf", disciplina.getUuid()).execute();
        com.espweb.chronos.network.model.Assunto nAssunto = response.body().getAssunto();
        assunto.setUuid(nAssunto.getUuid());
        assuntoBox.put(assunto);
    }
}
