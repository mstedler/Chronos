package com.espweb.chronos.data;

import android.content.Context;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;

import com.espweb.chronos.domain.model.Revisao;
import com.espweb.chronos.domain.repository.ArtefatoRepository;
import com.espweb.chronos.storage.boxes.RevisaoBox;
import com.espweb.chronos.storage.converters.StorageToDomainConverter;
import com.espweb.chronos.workers.api.CreateRevisaoWorker;
import com.espweb.chronos.workers.api.DeleteRevisaoWorker;
import com.espweb.chronos.workers.api.UpdateRevisaoWorker;
import com.espweb.chronos.workers.api.base.ApiWorkEnqueuer;
import com.espweb.chronos.workers.api.base.ApiWorkRequest;

import java.util.List;
import java.util.UUID;

public class RevisaoRepositoryImpl implements ArtefatoRepository<Revisao> {
    private Context context;
    private RevisaoBox box;

    public RevisaoRepositoryImpl(Context context) {
        this.context = context.getApplicationContext();
        box = new RevisaoBox();
    }

    @Override
    public long insert(Revisao revisao) {
        com.espweb.chronos.storage.model.Revisao sRevisao =
                new com.espweb.chronos.storage.model.Revisao(
                        UUID.randomUUID().toString(),
                        revisao.getData(),
                        revisao.getEscopo().getIntValue(),
                        revisao.getDescricao(),
                        revisao.getIdAssunto());
        long id = box.put(sRevisao);

        Data data = new Data.Builder().putLong(CreateRevisaoWorker.KEY_ID_REVISAO, id).build();
        OneTimeWorkRequest workRequest = new ApiWorkRequest(data, CreateRevisaoWorker.class).build();
        ApiWorkEnqueuer.enqueueUnique(context, workRequest);

        return id;
    }

    @Override
    public void update(Revisao revisao) {
        com.espweb.chronos.storage.model.Revisao sRevisao = box.get(revisao.getId());
        sRevisao.setEscopo(revisao.getEscopo().getIntValue());
        sRevisao.setData(revisao.getData());
        sRevisao.setDescricao(revisao.getDescricao());

        box.put(sRevisao);

        Data data = new Data.Builder().putLong(UpdateRevisaoWorker.KEY_ID_REVISAO, revisao.getId()).build();
        OneTimeWorkRequest workRequest = new ApiWorkRequest(data, UpdateRevisaoWorker.class).build();
        ApiWorkEnqueuer.enqueueUnique(context, workRequest);
    }

    @Override
    public void delete(Revisao revisao) {
        com.espweb.chronos.storage.model.Revisao aux = box.get(revisao.getId());
        box.remove(aux.getId());
        Data data = new Data.Builder().putString(DeleteRevisaoWorker.KEY_UIID_ARTEFATO, aux.getUuid()).build();
        OneTimeWorkRequest workRequest = new ApiWorkRequest(data, DeleteRevisaoWorker.class).build();
        ApiWorkEnqueuer.enqueueUnique(context, workRequest);
    }

    @Override
    public List<Revisao> getAll(long idAssunto) {
        List<com.espweb.chronos.storage.model.Revisao> revisoes = box.getAll(idAssunto);
        return StorageToDomainConverter.convertRevisoes(revisoes);
    }
}
