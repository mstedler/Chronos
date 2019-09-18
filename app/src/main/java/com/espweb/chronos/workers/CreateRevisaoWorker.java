package com.espweb.chronos.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.network.RestClient;
import com.espweb.chronos.network.model.Artefato;
import com.espweb.chronos.network.services.ArtefatoService;
import com.espweb.chronos.storage.boxes.RevisaoBox;
import com.espweb.chronos.storage.converters.StorageToNetworkConverter;
import com.espweb.chronos.storage.model.Revisao;
import com.espweb.chronos.workers.base.WebRequestWorker;

import java.io.IOException;

import retrofit2.Response;

public class CreateRevisaoWorker extends WebRequestWorker {
    public static final String KEY_ID_REVISAO = "KEY_ID_REVISAO";

    public CreateRevisaoWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public void work() throws IOException, NullPointerException, NotFoundException {
        RevisaoBox revisaoBox = new RevisaoBox();

        long id = getInputData().getLong(KEY_ID_REVISAO, -1);

        Revisao revisao = revisaoBox.get(id);

        Artefato artefato = new Artefato(StorageToNetworkConverter.convert(revisao));

        ArtefatoService artefatoService = RestClient.createService(ArtefatoService.class);

        Response<Artefato> response = artefatoService.create(artefato).execute();

        revisao.setUuid(response.body().getResponseRevisao().getUuid());

        revisaoBox.put(revisao);
    }
}
