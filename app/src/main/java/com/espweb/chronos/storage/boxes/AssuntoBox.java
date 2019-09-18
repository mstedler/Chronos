package com.espweb.chronos.storage.boxes;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.storage.database.ObjectBox;
import com.espweb.chronos.storage.model.Assunto;
import com.espweb.chronos.storage.model.Assunto_;

import java.util.List;

import io.objectbox.Box;

public class AssuntoBox {

    private static Box<Assunto> getBox() {
        return ObjectBox.get().boxFor(Assunto.class);
    }

    public long put(Assunto assunto) {
        return getBox().put(assunto);
    }

    public Assunto get(long id) throws NotFoundException {
        Assunto assunto = getBox().get(id);
        if (assunto == null) throw new NotFoundException("Assunto não encontrado : " + id);
        return assunto;
    }

    public void remove(long id) {
        getBox().remove(id);
    }

    public List<Assunto> getAll(long idDisciplina) {
        return getBox().query().eager(Assunto_.exercicios, Assunto_.revisoes, Assunto_.materiais).equal(Assunto_.disciplinaId, idDisciplina).and().isNull(Assunto_.deletedAt).build().find();
    }
}
