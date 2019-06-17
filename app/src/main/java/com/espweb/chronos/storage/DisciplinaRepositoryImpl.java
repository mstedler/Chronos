package com.espweb.chronos.storage;

import android.content.Context;

import com.espweb.chronos.domain.model.Disciplina;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.storage.converters.StorageModelConverter;
import com.espweb.chronos.storage.database.ObjectBox;
import com.espweb.chronos.storage.model.Disciplina_;

import java.util.List;
import java.util.UUID;

import io.objectbox.Box;

public class DisciplinaRepositoryImpl implements Repository<Disciplina> {

    private Context context;

    @Override
    public long insert(long parentId, Disciplina model) {
        com.espweb.chronos.storage.model.Disciplina disciplina =
                new com.espweb.chronos.storage.model.Disciplina(
                        UUID.randomUUID().toString(),
                        model.getNome(),
                        parentId);
        return getBox().put(disciplina);
    }

    @Override
    public boolean update(Disciplina model) {
        com.espweb.chronos.storage.model.Disciplina disciplina = getBox().get(model.getId());
        disciplina.setNome(model.getNome());
        getBox().put(disciplina);
        return true;
    }

    @Override
    public boolean delete(long id) {
        getBox().remove(id);
        return true;
    }

    @Override
    public Disciplina get(long id) {
        com.espweb.chronos.storage.model.Disciplina sDisciplina = getBox().get(id);
        return StorageModelConverter.ConvertToDomainModel(sDisciplina);
    }

    @Override
    public List<Disciplina> getAll(long parentId) {
        List<com.espweb.chronos.storage.model.Disciplina> disciplinas = getBox().query().equal(Disciplina_.cronogramaId, parentId).build().find();
        return StorageModelConverter.ConvertDisciplinasToDomainModel(disciplinas);
    }

    private Box<com.espweb.chronos.storage.model.Disciplina> getBox() {
        return ObjectBox.get().boxFor(com.espweb.chronos.storage.model.Disciplina.class);
    }
    public DisciplinaRepositoryImpl(Context context) {
        this.context = context;
    }
}
