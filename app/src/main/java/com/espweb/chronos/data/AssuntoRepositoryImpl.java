package com.espweb.chronos.data;

import android.content.Context;
import android.util.Log;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.domain.model.Assunto;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.storage.boxes.AssuntoBox;
import com.espweb.chronos.storage.converters.StorageToDomainConverter;
import com.espweb.chronos.workers.api.CreateAssuntoWorker;
import com.espweb.chronos.workers.api.DeleteAssuntoWorker;
import com.espweb.chronos.workers.api.UpdateAssuntoWorker;
import com.espweb.chronos.workers.api.base.ApiWorkEnqueuer;
import com.espweb.chronos.workers.api.base.ApiWorkRequest;

import java.util.List;
import java.util.UUID;

public class AssuntoRepositoryImpl implements Repository<Assunto> {

    private static final String TAG = "AssuntoRepositoryImp";
    private Context context;
    private AssuntoBox box;

    public AssuntoRepositoryImpl(Context context) {
        this.context = context.getApplicationContext();
        box = new AssuntoBox();
    }

    @Override
    public long insert(Assunto assunto) {
        com.espweb.chronos.storage.model.Assunto sAssunto = new com.espweb.chronos.storage.model.Assunto(
                UUID.randomUUID().toString(),
                assunto.getDescricao(),
                assunto.getIdDisciplina());

        long id = box.put(sAssunto);

        Data data = new Data.Builder().putLong(CreateAssuntoWorker.KEY_ID_ASSUNTO, id).build();

        OneTimeWorkRequest workRequest = new ApiWorkRequest(data, CreateAssuntoWorker.class).build();
        ApiWorkEnqueuer.enqueueUnique(context, workRequest);

        return id;
    }

    @Override
    public void update(Assunto assunto) {
        try {
            com.espweb.chronos.storage.model.Assunto sAssunto = box.get(assunto.getId());
            sAssunto.setDescricao(assunto.getDescricao());
            box.put(sAssunto);
            Data data = new Data.Builder().putLong(UpdateAssuntoWorker.KEY_ID_ASSUNTO, assunto.getId()).build();
            OneTimeWorkRequest workRequest = new ApiWorkRequest(data, UpdateAssuntoWorker.class).build();
            ApiWorkEnqueuer.enqueueUnique(context, workRequest);
        } catch (NotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void delete(long id) {
        try {
            com.espweb.chronos.storage.model.Assunto assunto = box.get(id);
            box.remove(id);

            Data data = new Data.Builder().putString(DeleteAssuntoWorker.KEY_UUID_ASSUNTO, assunto.getUuid()).build();
            OneTimeWorkRequest workRequest = new ApiWorkRequest(data, UpdateAssuntoWorker.class).build();
            ApiWorkEnqueuer.enqueueUnique(context, workRequest);
        } catch (NotFoundException e) {
            Log.e(TAG, e.getMessage());
        }

    }

    @Override
    public Assunto get(long id) {
        try {
            com.espweb.chronos.storage.model.Assunto assunto = box.get(id);
            return StorageToDomainConverter.convert(assunto);
        } catch (NotFoundException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    @Override
    public List<Assunto> getAll(long idDisciplina) {
        List<com.espweb.chronos.storage.model.Assunto> assuntos = box.getAll(idDisciplina);
        return StorageToDomainConverter.convertAssuntos(assuntos);
    }
}
