package com.espweb.chronos.storage;

import android.content.Context;

import com.espweb.chronos.domain.model.Revisao;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.storage.converters.StorageModelConverter;
import com.espweb.chronos.storage.database.ObjectBox;
import com.espweb.chronos.storage.model.Revisao_;

import java.util.List;
import java.util.UUID;

import io.objectbox.Box;

public class RevisaoRepositoryImpl implements Repository<Revisao> {
    private Context context;

    @Override
    public long insert(long parentId, Revisao model) {
        com.espweb.chronos.storage.model.Revisao revisao =
                new com.espweb.chronos.storage.model.Revisao(
                        UUID.randomUUID().toString(),
                        model.getData(),
                        model.getEscopo().getIntValue(),
                        parentId);
        return getBox().put(revisao);
    }

    @Override
    public boolean update(Revisao model) {
        com.espweb.chronos.storage.model.Revisao revisao = getBox().get(model.getId());
        revisao.setEscopo(model.getEscopo().getIntValue());
        getBox().put(revisao);
        return true;
    }

    @Override
    public boolean delete(long id) {
        getBox().remove(id);
        return true;
    }

    @Override
    public Revisao get(long id) {
        com.espweb.chronos.storage.model.Revisao revisao = getBox().get(id);
        return StorageModelConverter.ConvertToDomainModel(revisao);
    }

    @Override
    public List<Revisao> getAll(long parentId) {
        List<com.espweb.chronos.storage.model.Revisao> revisoes = getBox().query().equal(Revisao_.assuntoId, parentId).build().find();
        return StorageModelConverter.ConvertRevisoesToDomainModel(revisoes);
    }

    private Box<com.espweb.chronos.storage.model.Revisao> getBox() {
        return ObjectBox.get().boxFor(com.espweb.chronos.storage.model.Revisao.class);
    }

    public RevisaoRepositoryImpl(Context context) {
        this.context = context;
    }
}
