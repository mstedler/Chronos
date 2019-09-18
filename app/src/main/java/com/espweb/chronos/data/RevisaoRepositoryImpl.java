package com.espweb.chronos.data;

import android.content.Context;

import androidx.work.Data;

import com.espweb.chronos.domain.model.Revisao;
import com.espweb.chronos.domain.repository.ArtefatoRepository;
import com.espweb.chronos.storage.boxes.RevisaoBox;
import com.espweb.chronos.storage.converters.StorageToDomainConverter;
import com.espweb.chronos.workers.CreateRevisaoWorker;
import com.espweb.chronos.workers.DeleteRevisaoWorker;
import com.espweb.chronos.workers.UpdateMaterialWorker;
import com.espweb.chronos.workers.UpdateRevisaoWorker;
import com.espweb.chronos.workers.base.WorkFactory;

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

        WorkFactory.enqueue(context, data, CreateRevisaoWorker.class);

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

        WorkFactory.enqueue(context, data, UpdateRevisaoWorker.class);
    }

    @Override
    public void delete(Revisao revisao) {
        box.remove(revisao.getId());
        Data data = new Data.Builder().putString(DeleteRevisaoWorker.KEY_UIID_ARTEFATO, revisao.getUuid()).build();
        WorkFactory.enqueue(context, data, DeleteRevisaoWorker.class);
    }

    @Override
    public List<Revisao> getAll(long idAssunto) {
        List<com.espweb.chronos.storage.model.Revisao> revisoes = box.getAll(idAssunto);
        return StorageToDomainConverter.convertRevisoes(revisoes);
    }
}
