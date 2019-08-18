package com.espweb.chronos.data;

import android.content.Context;
import android.util.Log;

import androidx.work.Data;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.domain.model.Disciplina;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.storage.boxes.DisciplinaBox;
import com.espweb.chronos.storage.converters.StorageToDomainConverter;
import com.espweb.chronos.workers.CreateDisciplinaWorker;
import com.espweb.chronos.workers.DeleteDisciplinaWorker;
import com.espweb.chronos.workers.UpdateDisciplinaWorker;
import com.espweb.chronos.workers.base.WorkFactory;

import java.util.List;
import java.util.UUID;

public class DisciplinaRepositoryImpl implements Repository<Disciplina> {

    private static final String TAG = "DisciplinaRepository";
    private Context context;

    public DisciplinaRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public long insert(Disciplina disciplina) {
        com.espweb.chronos.storage.model.Disciplina sDisciplina =
                new com.espweb.chronos.storage.model.Disciplina(
                        UUID.randomUUID().toString(),
                        disciplina.getNome(),
                        disciplina.getDescricao(),
                        disciplina.getIdCronograma());

        long id = DisciplinaBox.put(sDisciplina);

        Data data = new Data.Builder().putLong(CreateDisciplinaWorker.KEY_ID_DISCIPLINA, id).build();

        WorkFactory.enqueue(context, data, CreateDisciplinaWorker.class);

        return id;
    }

    @Override
    public void update(Disciplina disciplina) {
        try {
            long id = disciplina.getId();
            com.espweb.chronos.storage.model.Disciplina sDisciplina = DisciplinaBox.get(id);
            sDisciplina.setNome(disciplina.getNome());
            sDisciplina.setDescricao(disciplina.getDescricao());
            DisciplinaBox.put(sDisciplina);
            Data data = new Data.Builder().putLong(CreateDisciplinaWorker.KEY_ID_DISCIPLINA, id).build();
            WorkFactory.enqueue(context, data, UpdateDisciplinaWorker.class);
        } catch (NotFoundException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    @Override
    public void delete(Disciplina disciplina) {
        DisciplinaBox.remove(disciplina.getId());
        Data data = new Data.Builder().putString(DeleteDisciplinaWorker.KEY_UUID_DISCIPLINA, disciplina.getUuid()).build();
        WorkFactory.enqueue(context, data, DeleteDisciplinaWorker.class);
    }

    @Override
    public Disciplina get(long id) {
        try {
            com.espweb.chronos.storage.model.Disciplina sDisciplina = DisciplinaBox.get(id);
            return StorageToDomainConverter.convert(sDisciplina);
        } catch (NotFoundException e) {
            Log.e(TAG, e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Disciplina> getAll(long idCronograma) {
        List<com.espweb.chronos.storage.model.Disciplina> disciplinas = DisciplinaBox.getAll(idCronograma);
        return StorageToDomainConverter.convertDisciplinas(disciplinas);
    }


}
