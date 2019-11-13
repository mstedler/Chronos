package com.espweb.chronos.workers.api;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.network.RestClient;
import com.espweb.chronos.network.model.Artefato;
import com.espweb.chronos.network.services.ArtefatoService;
import com.espweb.chronos.storage.boxes.MaterialBox;
import com.espweb.chronos.storage.converters.StorageToNetworkConverter;
import com.espweb.chronos.storage.model.Material;
import com.espweb.chronos.workers.api.base.ApiWorker;

import java.io.IOException;

import retrofit2.Response;

public class CreateMaterialWorker extends ApiWorker {
    public static final String ID_MATERIAL = "ID_MATERIAL";

    public CreateMaterialWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public void work() throws IOException, NullPointerException, NotFoundException {
        MaterialBox materialBox = new MaterialBox();

        long id = getInputData().getLong(ID_MATERIAL, -1);

        Material material = materialBox.get(id);

        Artefato artefato = new Artefato(StorageToNetworkConverter.convert(material));

        ArtefatoService artefatoService = RestClient.createService(ArtefatoService.class);

        Response<Artefato> response = artefatoService.create(artefato).execute();

        material.setUuid(response.body().getResponseMaterial().getUuid());

        materialBox.put(material);
    }
}
