package com.espweb.chronos.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.storage.boxes.MaterialBox;
import com.espweb.chronos.storage.model.Material;
import com.espweb.chronos.network.model.Artefato;
import com.espweb.chronos.workers.base.ApiWorker;
import com.espweb.chronos.storage.converters.StorageToNetworkConverter;
import com.espweb.chronos.network.RestClient;
import com.espweb.chronos.network.services.ArtefatoService;

import java.io.IOException;

public class UpdateMaterialWorker extends ApiWorker {
    public static String KEY_ID_MATERIAL = "KEY_ID_EXERCICIO";
    public UpdateMaterialWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public void work() throws IOException, NullPointerException, NotFoundException {
        long id = getInputData().getLong(KEY_ID_MATERIAL, 0);
        MaterialBox materialBox = new MaterialBox();

        Material material = materialBox.get(id);
        Artefato artefato = new Artefato();
        artefato.setPayloadMaterial(StorageToNetworkConverter.convert(material));

        ArtefatoService artefatoService = RestClient.createService(ArtefatoService.class);
        artefatoService.update(artefato, material.getUuid()).execute();
    }
}
