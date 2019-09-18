package com.espweb.chronos.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.network.RestClient;
import com.espweb.chronos.network.converters.DateConverter;
import com.espweb.chronos.network.services.CronogramaService;
import com.espweb.chronos.storage.boxes.CronogramaBox;
import com.espweb.chronos.storage.model.Cronograma;
import com.espweb.chronos.workers.base.WebRequestWorker;

import java.io.IOException;

import static com.espweb.chronos.workers.CreateCronogramaWorker.KEY_ID_CRONOGRAMA;

public class UpdateCronogramaWorker extends WebRequestWorker {

    public UpdateCronogramaWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public void work() throws IOException, NullPointerException, NotFoundException {
        CronogramaBox cronogramaBox = new CronogramaBox();
        long id = getInputData().getLong(KEY_ID_CRONOGRAMA, 0);
        Cronograma cronograma = cronogramaBox.get(id);
        CronogramaService cronogramaService = RestClient.createService(CronogramaService.class);
        cronogramaService.update(cronograma.getUuid(), cronograma.getTitulo(), cronograma.getDescricao(), DateConverter.format(cronograma.getInicio()), DateConverter.format(cronograma.getFim())).execute();

    }
}
