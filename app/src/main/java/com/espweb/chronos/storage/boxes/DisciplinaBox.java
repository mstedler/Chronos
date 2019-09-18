package com.espweb.chronos.storage.boxes;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.storage.database.ObjectBox;
import com.espweb.chronos.storage.model.Disciplina;
import com.espweb.chronos.storage.model.Disciplina_;

import java.util.List;

import io.objectbox.Box;

public class DisciplinaBox {

    private Box<Disciplina> getBox() {
        return ObjectBox.get().boxFor(Disciplina.class);
    }

    public long put(Disciplina disciplina) {
        return getBox().put(disciplina);
    }

    public Disciplina get(long id) throws NotFoundException {
        Disciplina disciplina = getBox().get(id);
        if (disciplina == null) throw new NotFoundException("Disciplina não encontrada : " + id);
        return disciplina;
    }

    public void remove(long id) {
        getBox().remove(id);
    }

    public List<Disciplina> getAll(long idCronograma) {
        return getBox().query().equal(Disciplina_.cronogramaId, idCronograma).and().isNull(Disciplina_.deletedAt).build().find();
    }
}
