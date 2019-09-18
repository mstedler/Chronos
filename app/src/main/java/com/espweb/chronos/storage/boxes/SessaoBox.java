package com.espweb.chronos.storage.boxes;

import com.espweb.chronos.storage.model.Sessao_;
import com.espweb.chronos.storage.model.User;
import com.espweb.chronos.storage.database.ObjectBox;
import com.espweb.chronos.storage.model.Sessao;

import io.objectbox.Box;

public class SessaoBox {

    public static Box<Sessao> getBox() {
        return ObjectBox.get().boxFor(Sessao.class);
    }

    public Sessao getActiveSession() {
        return getBox().query().equal(Sessao_.active, true).build().findFirst();
    }

    public void put(Sessao sessao) {
        getBox().put(sessao);
    }

    public User getActiveSessionUser() {
        return getActiveSession().getUser().getTarget();
    }
}
