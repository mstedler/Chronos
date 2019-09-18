package com.espweb.chronos.data;

import android.content.Context;

import androidx.work.Data;

import com.espweb.chronos.data.factory.ArtefatoRepositoryFactory;
import com.espweb.chronos.domain.exceptions.InvalidArtefatoException;
import com.espweb.chronos.domain.model.Artefato;
import com.espweb.chronos.domain.repository.ArtefatoRepository;
import com.espweb.chronos.storage.converters.StorageToDomainConverter;
import com.espweb.chronos.storage.database.ObjectBox;
import com.espweb.chronos.storage.model.Assunto;
import com.espweb.chronos.storage.model.Exercicio;
import com.espweb.chronos.storage.model.Material;
import com.espweb.chronos.storage.model.Revisao;
import com.espweb.chronos.workers.DeleteRevisaoWorker;
import com.espweb.chronos.workers.base.WorkFactory;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;

public class ArtefatoRepositoryImpl implements ArtefatoRepository<Artefato> {

    private static final String TAG = "ArtefatoRepositoryImpl";
    private Context context;

    public ArtefatoRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public long insert(Artefato artefato) throws InvalidArtefatoException {
        ArtefatoRepository artefatoRepository = ArtefatoRepositoryFactory.createRepository(artefato.getTipo(), context);
        return artefatoRepository.insert(artefato);
    }

    @Override
    public void update(Artefato artefato) throws InvalidArtefatoException {
        ArtefatoRepository artefatoRepository = ArtefatoRepositoryFactory.createRepository(artefato.getTipo(), context);
        artefatoRepository.update(artefato);

    }

    @Override
    public void delete(Artefato artefato) throws InvalidArtefatoException {
        ArtefatoRepository artefatoRepository = ArtefatoRepositoryFactory.createRepository(artefato.getTipo(), context);
        artefatoRepository.delete(artefato);
    }

    @Override
    public List<Artefato> getAll(long parentId) {
        List<Artefato> artefatos = new ArrayList<>();
        com.espweb.chronos.storage.model.Assunto assunto = getAssuntoBox().get(parentId);

        for (Revisao revisao : assunto.getRevisoes()) {
            artefatos.add(StorageToDomainConverter.convert(revisao));
        }

        for (Material material : assunto.getMateriais()) {
            artefatos.add(StorageToDomainConverter.convert(material));
        }

        for (Exercicio exercicio : assunto.getExercicios()) {
            artefatos.add(StorageToDomainConverter.convert(exercicio));
        }

        return artefatos;
    }

    private Box<Assunto> getAssuntoBox() {
        return ObjectBox.get().boxFor(Assunto.class);
    }
}
