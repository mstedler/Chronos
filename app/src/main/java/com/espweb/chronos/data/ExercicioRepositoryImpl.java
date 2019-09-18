package com.espweb.chronos.data;

import android.content.Context;

import androidx.work.Data;

import com.espweb.chronos.domain.model.Exercicio;
import com.espweb.chronos.domain.repository.ArtefatoRepository;
import com.espweb.chronos.storage.boxes.ExercicioBox;
import com.espweb.chronos.storage.converters.StorageToDomainConverter;
import com.espweb.chronos.storage.database.ObjectBox;
import com.espweb.chronos.storage.model.Exercicio_;
import com.espweb.chronos.workers.CreateExercicioWorker;
import com.espweb.chronos.workers.DeleteExercicioWorker;
import com.espweb.chronos.workers.DeleteRevisaoWorker;
import com.espweb.chronos.workers.UpdateExercicioWorker;
import com.espweb.chronos.workers.UpdateMaterialWorker;
import com.espweb.chronos.workers.base.WorkFactory;

import java.util.List;
import java.util.UUID;

import io.objectbox.Box;

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

        WorkFactory.enqueue(context, data, CreateExercicioWorker.class);

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

        WorkFactory.enqueue(context, data, UpdateExercicioWorker.class);
    }

    @Override
    public void delete(Exercicio exercicio) {
        box.remove(exercicio.getId());
        Data data = new Data.Builder().putString(DeleteRevisaoWorker.KEY_UIID_ARTEFATO, exercicio.getUuid()).build();
        WorkFactory.enqueue(context, data, DeleteExercicioWorker.class);
    }

    @Override
    public List<Exercicio> getAll(long idAssunto) {
        return StorageToDomainConverter.convertExercicios(box.getAll(idAssunto));
    }
}
