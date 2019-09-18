package com.espweb.chronos.data;

import android.content.Context;

import androidx.work.Data;

import com.espweb.chronos.domain.model.Material;
import com.espweb.chronos.domain.repository.ArtefatoRepository;
import com.espweb.chronos.storage.boxes.MaterialBox;
import com.espweb.chronos.storage.converters.StorageToDomainConverter;
import com.espweb.chronos.storage.database.ObjectBox;
import com.espweb.chronos.storage.model.Material_;
import com.espweb.chronos.workers.CreateMaterialWorker;
import com.espweb.chronos.workers.DeleteMaterialWorker;
import com.espweb.chronos.workers.DeleteRevisaoWorker;
import com.espweb.chronos.workers.UpdateMaterialWorker;
import com.espweb.chronos.workers.base.WorkFactory;

import java.util.List;
import java.util.UUID;

import io.objectbox.Box;

public class MaterialRepositoryImpl implements ArtefatoRepository<Material> {
    private Context context;
    private MaterialBox box;

    public MaterialRepositoryImpl(Context context) {
        this.context = context.getApplicationContext();
        box = new MaterialBox();
    }

    @Override
    public long insert(Material material) {
        com.espweb.chronos.storage.model.Material sMaterial =
                new com.espweb.chronos.storage.model.Material(
                        UUID.randomUUID().toString(),
                        material.getData(),
                        material.getDescricao(),
                        material.getMinutos(),
                        material.getEscopo().getIntValue(),
                        material.getIdAssunto());
        long id = box.put(sMaterial);

        Data data = new Data.Builder().putLong(CreateMaterialWorker.ID_MATERIAL, id).build();

        WorkFactory.enqueue(context, data, CreateMaterialWorker.class);

        return id;
    }

    @Override
    public void update(Material model) {
        com.espweb.chronos.storage.model.Material material = box.get(model.getId());
        material.setDescricao(model.getDescricao());
        material.setMinutos(model.getMinutos());
        material.setData(model.getData());
        material.setEscopo(model.getEscopo().getIntValue());
        box.put(material);

        Data data = new Data.Builder().putLong(UpdateMaterialWorker.KEY_ID_MATERIAL, model.getId()).build();

        WorkFactory.enqueue(context, data, UpdateMaterialWorker.class);
    }

    @Override
    public void delete(Material material) {
        box.remove(material.getId());
        Data data = new Data.Builder().putString(DeleteRevisaoWorker.KEY_UIID_ARTEFATO, material.getUuid()).build();
        WorkFactory.enqueue(context, data, DeleteMaterialWorker.class);
    }

    @Override
    public List<Material> getAll(long idAssunto) {
        List<com.espweb.chronos.storage.model.Material> materiais = box.getAll(idAssunto);
        return StorageToDomainConverter.convertMateriais(materiais);
    }
}
