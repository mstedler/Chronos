package com.espweb.chronos.storage;

import android.content.Context;

import com.espweb.chronos.domain.model.Assunto;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.storage.converters.StorageModelConverter;
import com.espweb.chronos.storage.database.ObjectBox;
import com.espweb.chronos.storage.model.Assunto_;

import java.util.List;

import io.objectbox.Box;

public class AssuntoRepositoryImpl implements Repository<Assunto> {

    private Context context;

    @Override
    public long insert(long parentId, Assunto model) {
        com.espweb.chronos.storage.model.Assunto assunto = new com.espweb.chronos.storage.model.Assunto(model.getDescricao(), model.getAnotacao(), parentId);
        return getBox().put(assunto);
    }

    @Override
    public boolean update(Assunto model) {
        com.espweb.chronos.storage.model.Assunto assunto = getBox().get(model.getId());
        assunto.setDescricao(model.getDescricao());
        assunto.setAnotacao(model.getAnotacao());
        getBox().put(assunto);
        return true;
    }

    @Override
    public boolean delete(long id) {
        getBox().remove(id);
        return true;
    }

    @Override
    public Assunto get(long id) {
        return StorageModelConverter.ConvertToDomainModel(getBox().get(id));
    }

    @Override
    public List<Assunto> getAll(long parentId) {
        List<com.espweb.chronos.storage.model.Assunto> assuntos = getBox().query().equal(Assunto_.disciplinaId, parentId).build().find();
        return StorageModelConverter.ConvertAssuntosToDomainModel(assuntos);
    }

    private Box<com.espweb.chronos.storage.model.Assunto> getBox() {
        return ObjectBox.get().boxFor(com.espweb.chronos.storage.model.Assunto.class);
    }

    public AssuntoRepositoryImpl(Context context) {
        this.context = context;
    }
}
