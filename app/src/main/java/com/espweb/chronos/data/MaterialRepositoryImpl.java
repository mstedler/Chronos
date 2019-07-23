package com.espweb.chronos.data;

import android.content.Context;

import com.espweb.chronos.domain.model.Material;
import com.espweb.chronos.domain.repository.ArtefatoRepository;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.storage.converters.StorageToDomainConverter;
import com.espweb.chronos.storage.database.ObjectBox;
import com.espweb.chronos.storage.model.Material_;

import java.util.List;
import java.util.UUID;

import io.objectbox.Box;

public class MaterialRepositoryImpl implements ArtefatoRepository<Material> {
    private Context context;

    @Override
    public long insert(Material material) {
        com.espweb.chronos.storage.model.Material sMaterial =
                new com.espweb.chronos.storage.model.Material(
                        UUID.randomUUID().toString(),
                        material.getData(),
                        material.getDescricao(),
                        material.getMinutos(),
                        material.getAssuntoId());
        return getBox().put(sMaterial);
    }

    @Override
    public void update(Material model) {
        com.espweb.chronos.storage.model.Material material = getBox().get(model.getId());
        material.setDescricao(model.getDescricao());
        material.setMinutos(model.getMinutos());
        material.setData(model.getData());
        getBox().put(material);
    }

    @Override
    public void delete(Material material) {
        getBox().remove(material.getId());
    }

    @Override
    public List<Material> getAll(long parentId) {
        List<com.espweb.chronos.storage.model.Material> materiais = getBox().query().equal(Material_.assuntoId, parentId).build().find();
        return StorageToDomainConverter.convertMateriais(materiais);
    }

    private Box<com.espweb.chronos.storage.model.Material> getBox(){
        return ObjectBox.get().boxFor(com.espweb.chronos.storage.model.Material.class);
    }

    public MaterialRepositoryImpl() {
    }
}
