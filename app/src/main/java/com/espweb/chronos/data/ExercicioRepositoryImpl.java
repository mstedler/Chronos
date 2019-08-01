package com.espweb.chronos.data;

import android.content.Context;

import com.espweb.chronos.domain.model.Exercicio;
import com.espweb.chronos.domain.repository.ArtefatoRepository;
import com.espweb.chronos.storage.converters.StorageToDomainConverter;
import com.espweb.chronos.storage.database.ObjectBox;
import com.espweb.chronos.storage.model.Exercicio_;

import java.util.List;
import java.util.UUID;

import io.objectbox.Box;

public class ExercicioRepositoryImpl implements ArtefatoRepository<Exercicio> {
    private Context context;

    @Override
    public long insert(Exercicio exercicio) {
        com.espweb.chronos.storage.model.Exercicio sExercicio =
                new com.espweb.chronos.storage.model.Exercicio(
                        UUID.randomUUID().toString(),
                        exercicio.getData(),
                        exercicio.getDescricao(),
                        exercicio.getQuantidade(),
                        exercicio.getAcertos(),
                        exercicio.getIdAssunto());
        return getBox().put(sExercicio);
    }

    @Override
    public void update(Exercicio model) {
        com.espweb.chronos.storage.model.Exercicio exercicio = getBox().get(model.getId());
        exercicio.setAcertos(model.getAcertos());
        exercicio.setDescricao(model.getDescricao());
        exercicio.setQuantidade(model.getQuantidade());
        exercicio.setData(model.getData());
        getBox().put(exercicio);
    }

    @Override
    public void delete(Exercicio exercicio) {
        getBox().remove(exercicio.getId());
    }

    @Override
    public List<Exercicio> getAll(long parentId) {
        List<com.espweb.chronos.storage.model.Exercicio> exercicios = getBox().query().equal(Exercicio_.assuntoId, parentId).build().find();
        return StorageToDomainConverter.convertExercicios(exercicios);
    }

    private Box<com.espweb.chronos.storage.model.Exercicio> getBox() {
        return ObjectBox.get().boxFor(com.espweb.chronos.storage.model.Exercicio.class);
    }

    public ExercicioRepositoryImpl() {
        this.context = context;
    }
}
