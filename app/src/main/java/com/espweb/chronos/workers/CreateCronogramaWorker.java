package com.espweb.chronos.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.network.RestClient;
import com.espweb.chronos.network.converters.DateConverter;
import com.espweb.chronos.network.model.Cronograma;
import com.espweb.chronos.network.services.CronogramaService;
import com.espweb.chronos.storage.boxes.CronogramaBox;
import com.espweb.chronos.workers.base.WebRequestWorker;

import java.io.IOException;

import retrofit2.Response;

public class CreateCronogramaWorker extends WebRequestWorker {
    public final static String KEY_ID_CRONOGRAMA = "ID_CRONOGRAMA";

    public CreateCronogramaWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public void work() throws IOException, NullPointerException, NotFoundException {
        CronogramaBox cronogramaBox = new CronogramaBox();
        long id = getInputData().getLong(KEY_ID_CRONOGRAMA, 0);
        com.espweb.chronos.storage.model.Cronograma cronograma = cronogramaBox.get(id);
        CronogramaService cronogramaService = RestClient.createService(CronogramaService.class);
        Response<Cronograma> response = cronogramaService.create(cronograma.getTitulo(),
                cronograma.getDescricao(),
                DateConverter.format(cronograma.getInicio()),
                DateConverter.format(cronograma.getFim())).execute();

        com.espweb.chronos.network.model.Cronograma nCronograma = response.body().getCronograma();
        cronograma.setUuid(nCronograma.getUuid());
        cronogramaBox.put(cronograma);
    }
}
