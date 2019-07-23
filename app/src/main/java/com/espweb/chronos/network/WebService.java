package com.espweb.chronos.network;

import android.util.Log;

import com.espweb.chronos.network.converters.RESTModelConverter;
import com.espweb.chronos.network.model.Cronograma;
import com.espweb.chronos.network.model.Cronogramas;
import com.espweb.chronos.network.services.CronogramaService;
import com.espweb.chronos.storage.converters.StorageToDomainConverter;
import com.espweb.chronos.storage.model.Cronograma_;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class WebService {
    public static List<Cronograma> getCronogramasCompletos() throws IOException {
        List<com.espweb.chronos.network.model.Cronograma> nCronogramas = new ArrayList<>();
        CronogramaService cronogramaService = RestClient.createService(CronogramaService.class);
        Response<Cronogramas> response = cronogramaService.getAllCronogramas().execute();
        if (response.isSuccessful()) {
            nCronogramas.addAll(response.body().getCronogramas());
        }
        return nCronogramas;
    }
}
