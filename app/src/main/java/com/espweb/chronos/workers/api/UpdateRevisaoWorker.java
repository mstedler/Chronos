package com.espweb.chronos.workers.api;

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
import com.espweb.chronos.workers.api.base.ApiWorker;

import java.io.IOException;

public class UpdateRevisaoWorker extends ApiWorker {

    public static String KEY_ID_REVISAO = "UUID_REVISAO";
    public UpdateRevisaoWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public void work() throws IOException, NullPointerException, NotFoundException {
        long id = getInputData().getLong(KEY_ID_REVISAO, 0);
        RevisaoBox revisaoBox = new RevisaoBox();

        Revisao revisao = revisaoBox.get(id);
        Artefato artefato = new Artefato();
        artefato.setPayloadRevisao(StorageToNetworkConverter.convert(revisao));

        ArtefatoService artefatoService = RestClient.createService(ArtefatoService.class);
        artefatoService.update(artefato, revisao.getUuid()).execute();
    }
}
