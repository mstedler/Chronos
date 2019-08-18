package com.espweb.chronos.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.network.RestClient;
import com.espweb.chronos.network.services.DisciplinaService;
import com.espweb.chronos.storage.boxes.DisciplinaBox;
import com.espweb.chronos.storage.model.Disciplina;
import com.espweb.chronos.workers.base.WebRequestWorker;

import java.io.IOException;

import static com.espweb.chronos.workers.CreateDisciplinaWorker.KEY_ID_DISCIPLINA;

public class DeleteDisciplinaWorker extends WebRequestWorker {
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
