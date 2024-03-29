package com.espweb.chronos.data;

import android.content.Context;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.espweb.chronos.domain.model.Material;
import com.espweb.chronos.domain.repository.ArtefatoRepository;
import com.espweb.chronos.storage.boxes.MaterialBox;
import com.espweb.chronos.storage.converters.StorageToDomainConverter;
import com.espweb.chronos.workers.MaterialNotificationWorker;
import com.espweb.chronos.workers.api.CreateMaterialWorker;
import com.espweb.chronos.workers.api.DeleteMaterialWorker;
import com.espweb.chronos.workers.api.UpdateMaterialWorker;
import com.espweb.chronos.workers.api.base.ApiWorkEnqueuer;
import com.espweb.chronos.workers.api.base.ApiWorkRequest;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
        OneTimeWorkRequest workRequest = new ApiWorkRequest(data, CreateMaterialWorker.class).build();
        ApiWorkEnqueuer.enqueueUnique(context, workRequest);

        if(material.isComecarAgora()) {

            Data materialData = new Data.Builder()
                    .putInt("escopo", material.getEscopo().getIntValue())
                    .putInt("minutos", material.getMinutos())
                    .build();

            OneTimeWorkRequest materialNotificationWork = new OneTimeWorkRequest
                    .Builder(MaterialNotificationWorker.class)
                    .setInputData(materialData)
                    .setInitialDelay(material.getMinutos(), TimeUnit.MINUTES)
                    .build();

            WorkManager.getInstance(context).enqueue(materialNotificationWork);

        }

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

        OneTimeWorkRequest workRequest = new ApiWorkRequest(data, UpdateMaterialWorker.class).build();
        ApiWorkEnqueuer.enqueueUnique(context, workRequest);
    }

    @Override
    public void delete(Material material) {
        com.espweb.chronos.storage.model.Material aux = box.get(material.getId());
        box.remove(aux.getId());
        Data data = new Data.Builder().putString(DeleteMaterialWorker.KEY_UIID_ARTEFATO, aux.getUuid()).build();
        OneTimeWorkRequest workRequest = new ApiWorkRequest(data, DeleteMaterialWorker.class).build();
        ApiWorkEnqueuer.enqueueUnique(context, workRequest);
    }

    @Override
    public List<Material> getAll(long idAssunto) {
        List<com.espweb.chronos.storage.model.Material> materiais = box.getAll(idAssunto);
        return StorageToDomainConverter.convertMateriais(materiais);
    }
}
