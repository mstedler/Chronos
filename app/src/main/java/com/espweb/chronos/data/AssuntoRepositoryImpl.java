package com.espweb.chronos.data;

import android.content.Context;
import android.util.Log;

import androidx.work.Data;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.domain.model.Assunto;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.storage.boxes.AssuntoBox;
import com.espweb.chronos.storage.converters.StorageToDomainConverter;
import com.espweb.chronos.workers.CreateAssuntoWorker;
import com.espweb.chronos.workers.DeleteAssuntoWorker;
import com.espweb.chronos.workers.UpdateAssuntoWorker;
import com.espweb.chronos.workers.base.WorkFactory;

import java.util.List;
import java.util.UUID;

public class AssuntoRepositoryImpl implements Repository<Assunto> {

    private static final String TAG = "AssuntoRepositoryImp";
    private Context context;

    public AssuntoRepositoryImpl(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public long insert(Assunto assunto) {
        com.espweb.chronos.storage.model.Assunto sAssunto = new com.espweb.chronos.storage.model.Assunto(
                UUID.randomUUID().toString(),
                assunto.getDescricao(),
                assunto.getIdDisciplina());

        long id = AssuntoBox.put(sAssunto);

        Data idData = new Data.Builder().putLong(CreateAssuntoWorker.KEY_ID_ASSUNTO, id).build();

        WorkFactory.enqueue(context, idData, CreateAssuntoWorker.class);

        return id;
    }

    @Override
    public void update(Assunto assunto) {
        try {
            com.espweb.chronos.storage.model.Assunto sAssunto = AssuntoBox.get(assunto.getId());
            sAssunto.setDescricao(assunto.getDescricao());
            AssuntoBox.put(sAssunto);
            Data idData = new Data.Builder().putLong(CreateAssuntoWorker.KEY_ID_ASSUNTO, assunto.getId()).build();
            WorkFactory.enqueue(context, idData, UpdateAssuntoWorker.class);
        } catch (NotFoundException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    @Override
    public void delete(Assunto assunto) {
        AssuntoBox.remove(assunto.getId());

        Data idData = new Data.Builder().putString(DeleteAssuntoWorker.KEY_UUID_ASSUNTO, assunto.getUuid()).build();

        WorkFactory.enqueue(context, idData, DeleteAssuntoWorker.class);
    }

    @Override
    public Assunto get(long id) {
        try {
            com.espweb.chronos.storage.model.Assunto assunto = AssuntoBox.get(id);
            return StorageToDomainConverter.convert(assunto);
        } catch (NotFoundException e) {
            Log.e(TAG, e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Assunto> getAll(long idDisciplina) {
        List<com.espweb.chronos.storage.model.Assunto> assuntos = AssuntoBox.getAll(idDisciplina);
        return StorageToDomainConverter.convertAssuntos(assuntos);
    }
}
