package com.espweb.chronos.storage;

import android.content.Context;
import android.util.Log;

import com.espweb.chronos.domain.exceptions.GetAllCronogramasException;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.repository.CronogramaRepository;
import com.espweb.chronos.network.RestClient;
import com.espweb.chronos.network.converters.RESTModelConverter;
import com.espweb.chronos.network.model.GetAllCronogramas;
import com.espweb.chronos.network.services.GetAllCronogramasService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class CronogramaRepositoryImpl implements CronogramaRepository {
    private Context context;

    public CronogramaRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public boolean insert(Cronograma model) {
        return false;
    }

    @Override
    public boolean update(Cronograma model) {
        return false;
    }

    @Override
    public Cronograma get(Object id) {
        return null;
    }

    @Override
    public List<Cronograma> getAllCronogramas() throws GetAllCronogramasException {
        List<Cronograma> cronogramas = new ArrayList<>();

        GetAllCronogramasService service = RestClient.createService(GetAllCronogramasService.class);

        try {
            //USER ID = 0 exemplo
            Response<GetAllCronogramas> response = service.getAllCronogramas(0).execute();
            cronogramas.addAll(RESTModelConverter.convertCronogramasToDomainModel(response.body().getCronogramas()));
        } catch (IOException e){
            Log.e("GetAllCronogramasSvc", e.getMessage());
            throw new GetAllCronogramasException(e.getMessage());
        }
        return cronogramas;
    }

    @Override
    public boolean delete(Cronograma model) {
        return false;
    }
}
