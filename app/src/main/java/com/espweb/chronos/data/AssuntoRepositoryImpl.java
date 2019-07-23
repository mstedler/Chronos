package com.espweb.chronos.data;

import android.content.Context;

import com.espweb.chronos.domain.model.Assunto;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.storage.converters.StorageToDomainConverter;
import com.espweb.chronos.storage.database.ObjectBox;
import com.espweb.chronos.storage.model.Assunto_;

import java.util.List;
import java.util.UUID;

import io.objectbox.Box;

public class AssuntoRepositoryImpl implements Repository<Assunto> {

    private Context context;

    @Override
    public long insert(Assunto assunto) {
        com.espweb.chronos.storage.model.Assunto sAssunto = new com.espweb.chronos.storage.model.Assunto(
                UUID.randomUUID().toString(),
                assunto.getDescricao(),
                assunto.getDisciplinaId());
        return getBox().put(sAssunto);
    }

    @Override
    public void update(Assunto assunto) {
        com.espweb.chronos.storage.model.Assunto sAssunto = getBox().get(assunto.getId());
        sAssunto.setDescricao(assunto.getDescricao());
        getBox().put(sAssunto);
    }

    @Override
    public void delete(Assunto assunto) {
        getBox().remove(assunto.getId());
    }

    @Override
    public Assunto get(long id) {
        com.espweb.chronos.storage.model.Assunto assunto = getBox().get(id);
        Assunto assunto1 = null;
        if(assunto != null) {
            assunto1 = StorageToDomainConverter.convert(assunto);
        }
        return assunto1;
    }

    @Override
    public List<Assunto> getAll(long parentId) {
        List<com.espweb.chronos.storage.model.Assunto> assuntos = getBox().query().equal(Assunto_.disciplinaId, parentId).build().find();
        return StorageToDomainConverter.convertAssuntos(assuntos);
    }

    private Box<com.espweb.chronos.storage.model.Assunto> getBox() {
        return ObjectBox.get().boxFor(com.espweb.chronos.storage.model.Assunto.class);
    }

    public AssuntoRepositoryImpl(Context context) {
        this.context = context;
    }

}
