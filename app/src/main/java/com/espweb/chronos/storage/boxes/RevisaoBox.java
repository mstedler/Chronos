package com.espweb.chronos.storage.boxes;

import com.espweb.chronos.storage.database.ObjectBox;
import com.espweb.chronos.storage.model.Revisao;
import com.espweb.chronos.storage.model.Revisao_;

import java.util.List;

import io.objectbox.Box;

public class RevisaoBox {
    private Box<Revisao> getBox() {
        return ObjectBox.get().boxFor(Revisao.class);
    }

    public long put(Revisao revisao) {
        return getBox().put(revisao);
    }

    public Revisao get(long id) {
        return getBox().get(id);
    }

    public void remove(long id) {
        getBox().remove(id);
    }

    public List<Revisao> getAll(long idAssunto) {
        return getBox().query().equal(Revisao_.assuntoId, idAssunto).build().find();
    }
}
