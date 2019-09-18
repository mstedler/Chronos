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

public class UpdateAssuntoWorker extends WebRequestWorker {
    public UpdateAssuntoWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public void work() throws IOException, NullPointerException, NotFoundException {
        AssuntoBox assuntoBox = new AssuntoBox();
        long id = getInputData().getLong(CreateAssuntoWorker.KEY_ID_ASSUNTO, 0);
        Assunto assunto = assuntoBox.get(id);
        Disciplina disciplina = assunto.getDisciplina().getTarget();
        AssuntoService assuntoService = RestClient.createService(AssuntoService.class);
        assuntoService.update(assunto.getUuid(), assunto.getDescricao(), "wtf", disciplina.getUuid()).execute();
    }
}
