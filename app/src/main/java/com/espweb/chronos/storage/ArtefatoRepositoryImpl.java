package com.espweb.chronos.storage;

import android.content.Context;

import com.espweb.chronos.domain.model.Artefato;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.presentation.ui.activities.AssuntoActivity;
import com.espweb.chronos.storage.converters.StorageToDomainConverter;
import com.espweb.chronos.storage.database.ObjectBox;
import com.espweb.chronos.storage.model.Assunto;
import com.espweb.chronos.storage.model.Exercicio;
import com.espweb.chronos.storage.model.Material;
import com.espweb.chronos.storage.model.Revisao;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;

public class ArtefatoRepositoryImpl implements Repository<Artefato> {

    private Context context;

    public ArtefatoRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public long insert(long parentId, Artefato model) {
        return 0;
    }

    @Override
    public boolean update(Artefato model) {
        return false;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Artefato get(long id) {
        return null;
    }

    @Override
    public List<Artefato> getAll(long parentId) {
        List<Artefato> artefatos = new ArrayList<>();

        com.espweb.chronos.storage.model.Assunto assunto = getAssuntoBox().get(parentId);

        for (Revisao revisao: assunto.getRevisoes()) {
            artefatos.add(StorageToDomainConverter.convert(revisao));
        }

        for(Material material: assunto.getMateriais()) {
            artefatos.add(StorageToDomainConverter.convert(material));
        }

        for(Exercicio exercicio: assunto.getExercicios()) {
            artefatos.add(StorageToDomainConverter.convert(exercicio));
        }

        return artefatos;
    }

    private Box<Assunto> getAssuntoBox() {
        return ObjectBox.get().boxFor(Assunto.class);
    }
}
