package com.espweb.chronos.data;

import android.content.Context;
import android.util.Log;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.domain.model.Disciplina;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.storage.boxes.DisciplinaBox;
import com.espweb.chronos.storage.converters.StorageToDomainConverter;
import com.espweb.chronos.workers.api.CreateDisciplinaWorker;
import com.espweb.chronos.workers.api.DeleteDisciplinaWorker;
import com.espweb.chronos.workers.api.UpdateDisciplinaWorker;
import com.espweb.chronos.workers.api.base.ApiWorkEnqueuer;
import com.espweb.chronos.workers.api.base.ApiWorkRequest;

import java.util.List;
import java.util.UUID;

public class DisciplinaRepositoryImpl implements Repository<Disciplina> {

    private static final String TAG = "DisciplinaRepository";
    private Context context;
    private DisciplinaBox box;
    public DisciplinaRepositoryImpl(Context context) {
        this.context = context;
        box = new DisciplinaBox();
    }

    @Override
    public long insert(Disciplina disciplina) {
        com.espweb.chronos.storage.model.Disciplina sDisciplina =
                new com.espweb.chronos.storage.model.Disciplina(
                        UUID.randomUUID().toString(),
                        disciplina.getNome(),
                        disciplina.getDescricao(),
                        disciplina.getIdCronograma());

        long id = box.put(sDisciplina);

        Data data = new Data.Builder().putLong(CreateDisciplinaWorker.KEY_ID_DISCIPLINA, id).build();

        OneTimeWorkRequest workRequest = new ApiWorkRequest(data, CreateDisciplinaWorker.class).build();
        ApiWorkEnqueuer.enqueueUnique(context, workRequest);

        return id;
    }

    @Override
    public void update(Disciplina disciplina) {
        try {
            long id = disciplina.getId();
            com.espweb.chronos.storage.model.Disciplina sDisciplina = box.get(id);
            sDisciplina.setNome(disciplina.getNome());
            sDisciplina.setDescricao(disciplina.getDescricao());
            box.put(sDisciplina);
            Data data = new Data.Builder().putLong(UpdateDisciplinaWorker.KEY_ID_DISCIPLINA, id).build();
            OneTimeWorkRequest workRequest = new ApiWorkRequest(data, UpdateDisciplinaWorker.class).build();
            ApiWorkEnqueuer.enqueueUnique(context, workRequest);
        } catch (NotFoundException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    @Override
    public void delete(long id) {
        try {
            com.espweb.chronos.storage.model.Disciplina disciplina = box.get(id);
            box.remove(id);
            Data data = new Data.Builder().putString(DeleteDisciplinaWorker.KEY_UUID_DISCIPLINA, disciplina.getUuid()).build();
            OneTimeWorkRequest workRequest = new ApiWorkRequest(data, DeleteDisciplinaWorker.class).build();
            ApiWorkEnqueuer.enqueueUnique(context, workRequest);
        } catch (NotFoundException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    @Override
    public Disciplina get(long id) {
        try {
            com.espweb.chronos.storage.model.Disciplina sDisciplina = box.get(id);
            return StorageToDomainConverter.convert(sDisciplina);
        } catch (NotFoundException e) {
            Log.e(TAG, e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Disciplina> getAll(long idCronograma) {
        List<com.espweb.chronos.storage.model.Disciplina> disciplinas = box.getAll(idCronograma);
        return StorageToDomainConverter.convertDisciplinas(disciplinas);
    }


}
