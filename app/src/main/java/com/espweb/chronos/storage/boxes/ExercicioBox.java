package com.espweb.chronos.storage.boxes;

import com.espweb.chronos.storage.database.ObjectBox;
import com.espweb.chronos.storage.model.Exercicio;
import com.espweb.chronos.storage.model.Exercicio_;

import java.util.List;

import io.objectbox.Box;

public class ExercicioBox {
    private Box<Exercicio> getBox() {
        return ObjectBox.get().boxFor(Exercicio.class);
    }

    public long put(Exercicio exercicio) {
        return getBox().put(exercicio);
    }

    public Exercicio get(long id) {
        return getBox().get(id);
    }

    public void remove(long id) {
        getBox().remove(id);
    }

    public List<Exercicio> getAll(long idAssunto) {
        return getBox().query().equal(Exercicio_.assuntoId, idAssunto).build().find();
    }
}
