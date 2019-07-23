package com.espweb.chronos.data;

import android.content.Context;

import com.espweb.chronos.domain.model.Disciplina;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.storage.converters.StorageToDomainConverter;
import com.espweb.chronos.storage.database.ObjectBox;
import com.espweb.chronos.storage.model.Disciplina_;

import java.util.List;
import java.util.UUID;

import io.objectbox.Box;

public class DisciplinaRepositoryImpl implements Repository<Disciplina> {

    private Context context;

    @Override
    public long insert(Disciplina disciplina) {
        com.espweb.chronos.storage.model.Disciplina sDisciplina =
                new com.espweb.chronos.storage.model.Disciplina(
                        UUID.randomUUID().toString(),
                        disciplina.getNome(),
                        disciplina.getDescricao(),
                        disciplina.getCronogramaId());
        return getBox().put(sDisciplina);
    }

    @Override
    public void update(Disciplina disciplina) {
        com.espweb.chronos.storage.model.Disciplina sDisciplina = getBox().get(disciplina.getId());
        sDisciplina.setNome(disciplina.getNome());
        getBox().put(sDisciplina);
    }

    @Override
    public void delete(Disciplina disciplina) {
        getBox().remove(disciplina.getId());
    }

    @Override
    public Disciplina get(long id) {
        com.espweb.chronos.storage.model.Disciplina sDisciplina = getBox().get(id);
        return StorageToDomainConverter.convert(sDisciplina);
    }

    @Override
    public List<Disciplina> getAll(long parentId) {
        List<com.espweb.chronos.storage.model.Disciplina> disciplinas = getBox().query().equal(Disciplina_.cronogramaId, parentId).build().find();
        return StorageToDomainConverter.convertDisciplinas(disciplinas);
    }

    private Box<com.espweb.chronos.storage.model.Disciplina> getBox() {
        return ObjectBox.get().boxFor(com.espweb.chronos.storage.model.Disciplina.class);
    }
    public DisciplinaRepositoryImpl(Context context) {
        this.context = context;
    }
}
