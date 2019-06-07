package com.espweb.chronos.domain.interactors.material.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.material.DeleteMaterialInteractor;
import com.espweb.chronos.domain.model.Material;
import com.espweb.chronos.domain.repository.Repository;

public class DeleteMaterialInteractorImpl extends AbstractInteractor implements DeleteMaterialInteractor {

    private Repository<Material> materialRepository;
    private Callback callback;
    private long materialId;


    public DeleteMaterialInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                        Callback callback,
                                        Repository<Material> materialRepository,
                                        long materialId) {
        super(threadExecutor, mainThread);

        if (materialRepository == null || callback == null) {
            throw new IllegalArgumentException("Argumentos nao podem ser nulos!");
        }

        this.materialRepository = materialRepository;
        this.callback = callback;
        this.materialId = materialId;
    }

    @Override
    public void run() {
        boolean success = materialRepository.delete(materialId);
        if(success) {
            mainThread.post(() -> callback.onMaterialDeleted());
        } else {
            mainThread.post(() -> callback.onError("Erro ao excluir material"));
        }
    }
}
