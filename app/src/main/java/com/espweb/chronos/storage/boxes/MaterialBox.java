package com.espweb.chronos.storage.boxes;

import com.espweb.chronos.storage.database.ObjectBox;
import com.espweb.chronos.storage.model.Material;
import com.espweb.chronos.storage.model.Material_;

import java.util.List;

import io.objectbox.Box;

public class MaterialBox {
    private Box<Material> getBox() {
        return ObjectBox.get().boxFor(Material.class);
    }

    public long put(Material material) {
        return getBox().put(material);
    }

    public Material get(long id) {
        return getBox().get(id);
    }

    public void remove(long id) {
        getBox().remove(id);
    }

    public List<Material> getAll(long idAssunto) {
        return getBox().query().equal(Material_.assuntoId, idAssunto).build().find();
    }
}
