package com.espweb.chronos.storage;

import android.content.Context;

import com.espweb.chronos.domain.model.Material;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.storage.converters.StorageModelConverter;
import com.espweb.chronos.storage.database.ObjectBox;
import com.espweb.chronos.storage.model.Material_;

import java.util.List;
import java.util.UUID;

import io.objectbox.Box;

public class MaterialRepositoryImpl implements Repository<Material> {
    private Context context;

    @Override
    public long insert(long parentId, Material model) {
        com.espweb.chronos.storage.model.Material material =
                new com.espweb.chronos.storage.model.Material(
                        UUID.randomUUID().toString(),
                        model.getData(),
                        model.getDescricao(),
                        model.getMinutos(),
                        parentId);
        return getBox().put(material);
    }

    @Override
    public boolean update(Material model) {
        com.espweb.chronos.storage.model.Material material = getBox().get(model.getId());
        material.setDescricao(model.getDescricao());
        material.setMinutos(model.getMinutos());
        material.setData(model.getData());
        getBox().put(material);
        return true;
    }

    @Override
    public boolean delete(long id) {
        getBox().remove(id);
        return true;
    }

    @Override
    public Material get(long id) {
        com.espweb.chronos.storage.model.Material material = getBox().get(id);
        return StorageModelConverter.ConvertToDomainModel(material);
    }

    @Override
    public List<Material> getAll(long parentId) {
        List<com.espweb.chronos.storage.model.Material> materiais = getBox().query().equal(Material_.assuntoId, parentId).build().find();
        return StorageModelConverter.ConvertMateriaisToDomainModel(materiais);
    }

    private Box<com.espweb.chronos.storage.model.Material> getBox(){
        return ObjectBox.get().boxFor(com.espweb.chronos.storage.model.Material.class);
    }

    public MaterialRepositoryImpl(Context context) {
        this.context = context;
    }
}
