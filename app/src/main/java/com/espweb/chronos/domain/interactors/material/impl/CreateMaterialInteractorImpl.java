package com.espweb.chronos.domain.interactors.material.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.material.CreateMaterialInteractor;
import com.espweb.chronos.domain.model.Material;
import com.espweb.chronos.domain.repository.Repository;

public class CreateMaterialInteractorImpl extends AbstractInteractor implements CreateMaterialInteractor {

    private Repository<Material> materialRepository;
    private Callback callback;
    private Material material;
    private long assuntoId;

    public CreateMaterialInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                        Callback callback,
                                        Repository<Material> materialRepository,
                                        Material material,
                                        long assuntoId) {
        super(threadExecutor, mainThread);

        if (materialRepository == null || callback == null) {
            throw new IllegalArgumentException("Argumentos nao podem ser nulos!");
        }

        this.materialRepository = materialRepository;
        this.callback = callback;
        this.material = material;
        this.assuntoId = assuntoId;
    }

    @Override
    public void run() {
        long materialId = materialRepository.insert(assuntoId, material);

        if(materialId != 0) {
            mainThread.post(() -> callback.onMaterialCreated(materialId));
        } else {
            mainThread.post(() -> callback.onError("Erro ao criar material."));
        }
    }
}
