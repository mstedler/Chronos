package com.espweb.chronos.storage;

import android.content.Context;

import com.espweb.chronos.domain.model.Exercicio;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.storage.converters.StorageToDomainConverter;
import com.espweb.chronos.storage.database.ObjectBox;
import com.espweb.chronos.storage.model.Exercicio_;

import java.util.List;
import java.util.UUID;

import io.objectbox.Box;

public class ExercicioRepositoryImpl implements Repository<Exercicio> {
    private Context context;

    @Override
    public long insert(long parentId, Exercicio model) {
        com.espweb.chronos.storage.model.Exercicio exercicio =
                new com.espweb.chronos.storage.model.Exercicio(
                        UUID.randomUUID().toString(),
                        model.getData(),
                        model.getDescricao(),
                        model.getQuantidade(),
                        model.getAcertos(),
                        parentId);
        return getBox().put(exercicio);
    }

    @Override
    public boolean update(Exercicio model) {
        com.espweb.chronos.storage.model.Exercicio exercicio = getBox().get(model.getId());
        exercicio.setAcertos(model.getAcertos());
        exercicio.setDescricao(model.getDescricao());
        exercicio.setQuantidade(model.getQuantidade());
        exercicio.setData(model.getData());
        getBox().put(exercicio);
        return true;
    }

    @Override
    public boolean delete(long id) {
        getBox().remove(id);
        return true;
    }

    @Override
    public Exercicio get(long id) {
        return StorageToDomainConverter.convert(getBox().get(id));
    }

    @Override
    public List<Exercicio> getAll(long parentId) {
        List<com.espweb.chronos.storage.model.Exercicio> exercicios = getBox().query().equal(Exercicio_.assuntoId, parentId).build().find();
        return StorageToDomainConverter.convertExercicios(exercicios);
    }

    private Box<com.espweb.chronos.storage.model.Exercicio> getBox() {
        return ObjectBox.get().boxFor(com.espweb.chronos.storage.model.Exercicio.class);
    }

    public ExercicioRepositoryImpl(Context context) {
        this.context = context;
    }
}
