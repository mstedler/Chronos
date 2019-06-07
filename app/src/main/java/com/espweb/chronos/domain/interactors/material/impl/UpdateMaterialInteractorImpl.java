package com.espweb.chronos.domain.interactors.material.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.material.UpdateMaterialInteractor;
import com.espweb.chronos.domain.model.Material;
import com.espweb.chronos.domain.repository.Repository;

public class UpdateMaterialInteractorImpl extends AbstractInteractor implements UpdateMaterialInteractor {

    private Repository<Material> materialRepository;
    private Callback callback;
    private Material material;

    public UpdateMaterialInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                        Callback callback,
                                        Repository<Material> materialRepository,
                                        Material material) {
        super(threadExecutor, mainThread);
        if (materialRepository == null || callback == null) {
            throw new IllegalArgumentException("Argumentos nao podem ser nulos!");
        }
        this.materialRepository = materialRepository;
        this.callback = callback;
        this.material = material;
    }

    @Override
    public void run() {
        boolean success = materialRepository.update(material);
        if(success) {
            mainThread.post(() -> callback.onMaterialUpdated());
        } else {
            mainThread.post(() -> callback.onError("Erro ao atualizar material."));
        }
    }
}
