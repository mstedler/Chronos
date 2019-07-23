package com.espweb.chronos.data;

import android.content.Context;

import com.espweb.chronos.domain.model.Revisao;
import com.espweb.chronos.domain.repository.ArtefatoRepository;
import com.espweb.chronos.storage.converters.StorageToDomainConverter;
import com.espweb.chronos.storage.database.ObjectBox;
import com.espweb.chronos.storage.model.Revisao_;

import java.util.List;
import java.util.UUID;

import io.objectbox.Box;

public class RevisaoRepositoryImpl implements ArtefatoRepository<Revisao> {
    private Context context;

    @Override
    public long insert(Revisao revisao) {
        com.espweb.chronos.storage.model.Revisao sRevisao =
                new com.espweb.chronos.storage.model.Revisao(
                        UUID.randomUUID().toString(),
                        revisao.getData(),
                        revisao.getEscopo().getIntValue(),
                        revisao.getAssuntoId());
        return getBox().put(sRevisao);
    }

    @Override
    public void update(Revisao model) {
        com.espweb.chronos.storage.model.Revisao revisao = getBox().get(model.getId());
        revisao.setEscopo(model.getEscopo().getIntValue());
        getBox().put(revisao);
    }

    @Override
    public void delete(Revisao revisao) {
        getBox().remove(revisao.getId());
    }

    @Override
    public List<Revisao> getAll(long parentId) {
        List<com.espweb.chronos.storage.model.Revisao> revisoes = getBox().query().equal(Revisao_.assuntoId, parentId).build().find();
        return StorageToDomainConverter.convertRevisoes(revisoes);
    }

    private Box<com.espweb.chronos.storage.model.Revisao> getBox() {
        return ObjectBox.get().boxFor(com.espweb.chronos.storage.model.Revisao.class);
    }

    public RevisaoRepositoryImpl() {
        this.context = context;
    }
}
