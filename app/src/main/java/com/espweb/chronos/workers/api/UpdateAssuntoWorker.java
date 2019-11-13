package com.espweb.chronos.workers.api;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.network.RestClient;
import com.espweb.chronos.network.services.AssuntoService;
import com.espweb.chronos.storage.boxes.AssuntoBox;
import com.espweb.chronos.storage.model.Assunto;
import com.espweb.chronos.storage.model.Disciplina;
import com.espweb.chronos.workers.api.base.ApiWorker;

import java.io.IOException;

public class UpdateAssuntoWorker extends ApiWorker {
    public static final String KEY_ID_ASSUNTO = "ID_ASSUNTO";

    public UpdateAssuntoWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public void work() throws IOException, NullPointerException, NotFoundException {
        AssuntoBox assuntoBox = new AssuntoBox();
        long id = getInputData().getLong(KEY_ID_ASSUNTO, 0);
        Assunto assunto = assuntoBox.get(id);
        Disciplina disciplina = assunto.getDisciplina().getTarget();
        AssuntoService assuntoService = RestClient.createService(AssuntoService.class);
        assuntoService.update(assunto.getUuid(), assunto.getDescricao(), "wtf", disciplina.getUuid()).execute();
    }
}
