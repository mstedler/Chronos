package com.espweb.chronos.data;

import android.content.Context;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;

import com.espweb.chronos.domain.model.Exercicio;
import com.espweb.chronos.domain.repository.ArtefatoRepository;
import com.espweb.chronos.storage.boxes.ExercicioBox;
import com.espweb.chronos.storage.converters.StorageToDomainConverter;
import com.espweb.chronos.workers.CreateExercicioWorker;
import com.espweb.chronos.workers.CreateRevisaoWorker;
import com.espweb.chronos.workers.DeleteExercicioWorker;
import com.espweb.chronos.workers.DeleteRevisaoWorker;
import com.espweb.chronos.workers.UpdateExercicioWorker;
import com.espweb.chronos.workers.base.ApiWorkEnqueuer;
import com.espweb.chronos.workers.base.ApiWorkRequest;

import java.util.List;
import java.util.UUID;

public class ExercicioRepositoryImpl implements ArtefatoRepository<Exercicio> {
    private Context context;
    private ExercicioBox box;

    public ExercicioRepositoryImpl(Context context) {
        this.context = context.getApplicationContext();
        box = new ExercicioBox();
    }

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
        long id =  box.put(sExercicio);

        Data data = new Data.Builder().putLong(CreateExercicioWorker.ID_EXERCICIO, id).build();

        OneTimeWorkRequest workRequest = new ApiWorkRequest(data, CreateExercicioWorker.class).build();
        ApiWorkEnqueuer.enqueueUnique(context, workRequest);

        return id;
    }

    @Override
    public void update(Exercicio model) {
        com.espweb.chronos.storage.model.Exercicio exercicio = box.get(model.getId());
        exercicio.setAcertos(model.getAcertos());
        exercicio.setDescricao(model.getDescricao());
        exercicio.setQuantidade(model.getQuantidade());
        exercicio.setData(model.getData());
        box.put(exercicio);

        Data data = new Data.Builder().putLong(UpdateExercicioWorker.KEY_ID_EXERCICIO, model.getId()).build();

        OneTimeWorkRequest workRequest = new ApiWorkRequest(data, UpdateExercicioWorker.class).build();
        ApiWorkEnqueuer.enqueueUnique(context, workRequest);
    }

    @Override
    public void delete(Exercicio exercicio) {
        com.espweb.chronos.storage.model.Exercicio aux = box.get(exercicio.getId());
        box.remove(aux.getId());
        Data data = new Data.Builder().putString(DeleteExercicioWorker.KEY_UIID_ARTEFATO, aux.getUuid()).build();
        OneTimeWorkRequest workRequest = new ApiWorkRequest(data, DeleteExercicioWorker.class).build();
        ApiWorkEnqueuer.enqueueUnique(context, workRequest);
    }

    @Override
    public List<Exercicio> getAll(long idAssunto) {
        return StorageToDomainConverter.convertExercicios(box.getAll(idAssunto));
    }
}
