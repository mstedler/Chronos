package com.espweb.chronos.data;

import android.content.Context;
import android.util.Log;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.repository.CronogramaRepository;
import com.espweb.chronos.network.RestClient;
import com.espweb.chronos.network.converters.NetworkToStorageConverter;
import com.espweb.chronos.network.model.Cronogramas;
import com.espweb.chronos.network.services.CronogramaService;
import com.espweb.chronos.network.utils.Connection;
import com.espweb.chronos.storage.boxes.CronogramaBox;
import com.espweb.chronos.storage.converters.DomainToStorageConverter;
import com.espweb.chronos.storage.converters.StorageToDomainConverter;
import com.espweb.chronos.workers.api.CreateCronogramaWorker;
import com.espweb.chronos.workers.api.DeleteCronogramaWorker;
import com.espweb.chronos.workers.api.UpdateCronogramaWorker;
import com.espweb.chronos.workers.api.base.ApiWorkEnqueuer;
import com.espweb.chronos.workers.api.base.ApiWorkRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import retrofit2.Response;

public class CronogramaRepositoryImpl implements CronogramaRepository {

    private static final String TAG = "CronogramaRepository";
    private Context context;
    private CronogramaBox box;

    public CronogramaRepositoryImpl(Context context) {
        this.context = context.getApplicationContext();
        box = new CronogramaBox();
    }

    @Override
    public long insert(Cronograma cronograma) {
        com.espweb.chronos.storage.model.Cronograma sCronograma = DomainToStorageConverter.convert(cronograma);
        sCronograma.setUuid(UUID.randomUUID().toString());
        long id = box.put(sCronograma);

        Data data = new Data.Builder().putLong(CreateCronogramaWorker.KEY_ID_CRONOGRAMA, id).build();
        OneTimeWorkRequest workRequest = new ApiWorkRequest(data, CreateCronogramaWorker.class).build();
        ApiWorkEnqueuer.enqueueUnique(context, workRequest);
        return id;
    }

    @Override
    public void update(Cronograma cronograma) {
        try {
            com.espweb.chronos.storage.model.Cronograma sCronograma = box.get(cronograma.getId());
            sCronograma.setTitulo(cronograma.getTitulo());
            sCronograma.setDescricao(cronograma.getDescricao());
            sCronograma.setInicio(cronograma.getInicio());
            sCronograma.setFim(cronograma.getFim());
            box.put(sCronograma);
            Data data = new Data.Builder().putLong(UpdateCronogramaWorker.KEY_ID_CRONOGRAMA, cronograma.getId()).build();
            OneTimeWorkRequest workRequest = new ApiWorkRequest(data, UpdateCronogramaWorker.class).build();
            ApiWorkEnqueuer.enqueueUnique(context, workRequest);
        } catch (NotFoundException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    @Override
    public Cronograma get(long id) {
        try {
            com.espweb.chronos.storage.model.Cronograma sCronograma = box.get(id);
            return StorageToDomainConverter.convert(sCronograma);
        } catch (NotFoundException e) {
            return null;
        }
    }

    @Override
    public void delete(long id) {
        try {
            com.espweb.chronos.storage.model.Cronograma sCronograma = box.get(id);
            box.remove(sCronograma.getId());

            Data data = new Data.Builder().putString(DeleteCronogramaWorker.KEY_UUID_CRONOGRAMA, sCronograma.getUuid()).build();
            OneTimeWorkRequest workRequest = new ApiWorkRequest(data, DeleteCronogramaWorker.class).build();
            ApiWorkEnqueuer.enqueueUnique(context, workRequest);
        } catch (Exception e) {

        }

    }

    @Override
    public List<Cronograma> getAll(long userId) {
        List<com.espweb.chronos.storage.model.Cronograma> sCronogramas = box.getAll(userId);
        return new ArrayList<>(StorageToDomainConverter.convertCronogramas(sCronogramas));

    }

    @Override
    public List<Cronograma> getAll(long userId, boolean fetchFromWeb) {
        List<com.espweb.chronos.storage.model.Cronograma> sCronogramas = box.getAll(userId);
        if (fetchFromWeb && Connection.isOnline()) {
            List<com.espweb.chronos.network.model.Cronograma> nCronogramas = fetchFromWeb();
            if (nCronogramas != null) {
                sCronogramas = replaceExisting(userId, sCronogramas, nCronogramas);
            }
        }
        return StorageToDomainConverter.convertCronogramas(sCronogramas).stream().sorted(Comparator.comparing(Cronograma::getInicio)).collect(Collectors.toList());
    }

    @Override
    public int getCronogramasCount(long userId) {
        return box.getCount(userId);
    }

    private List<com.espweb.chronos.storage.model.Cronograma> replaceExisting(long userId, List<com.espweb.chronos.storage.model.Cronograma> sCronogramas, List<com.espweb.chronos.network.model.Cronograma> nCronogramas) {
        box.deleteCascade(sCronogramas);
        sCronogramas = NetworkToStorageConverter.convertCronogramas(nCronogramas);
        for (com.espweb.chronos.storage.model.Cronograma cronograma : sCronogramas) {
            cronograma.getUser().setTargetId(userId);
        }
        box.put(sCronogramas);
        return sCronogramas;
    }

    private List<com.espweb.chronos.network.model.Cronograma> fetchFromWeb() {
        try {
            CronogramaService cronogramaService = RestClient.createService(CronogramaService.class);
            Response<Cronogramas> response = cronogramaService.getCronogramasCompletos().execute();
            return new ArrayList<>(response.body().getCronogramas());
        } catch (IOException | NullPointerException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }


}
