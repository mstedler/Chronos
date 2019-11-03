package com.espweb.chronos.data;

import android.content.Context;

import com.espweb.chronos.domain.exceptions.InvalidArtefatoException;
import com.espweb.chronos.domain.model.Artefato;
import com.espweb.chronos.domain.model.EnumTipo;
import com.espweb.chronos.domain.repository.ArtefatoRepository;
import com.espweb.chronos.storage.converters.StorageToDomainConverter;
import com.espweb.chronos.storage.database.ObjectBox;
import com.espweb.chronos.storage.model.Assunto;
import com.espweb.chronos.storage.model.Exercicio;
import com.espweb.chronos.storage.model.Material;
import com.espweb.chronos.storage.model.Revisao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.objectbox.Box;

public class ArtefatoRepositoryImpl implements ArtefatoRepository<Artefato> {

    private static final String TAG = "ArtefatoRepositoryImpl";
    private HashMap<EnumTipo, ArtefatoRepository> artefatoRepositories;

    public ArtefatoRepositoryImpl(Context context) {
        artefatoRepositories = new HashMap<EnumTipo, ArtefatoRepository>() {{
           put(EnumTipo.MATERIAL, new MaterialRepositoryImpl(context));
           put(EnumTipo.REVISAO, new RevisaoRepositoryImpl(context));
           put(EnumTipo.EXERCICIO, new ExercicioRepositoryImpl(context));
        }};
    }

    @Override
    public long insert(Artefato artefato) throws InvalidArtefatoException {
        ArtefatoRepository artefatoRepository = artefatoRepositories.get(artefato.getTipo());
        return artefatoRepository.insert(artefato);
    }

    @Override
    public void update(Artefato artefato) throws InvalidArtefatoException {
        ArtefatoRepository artefatoRepository = artefatoRepositories.get(artefato.getTipo());
        artefatoRepository.update(artefato);

    }

    @Override
    public void delete(Artefato artefato) throws InvalidArtefatoException {
        ArtefatoRepository artefatoRepository = artefatoRepositories.get(artefato.getTipo());
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
