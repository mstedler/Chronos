package com.espweb.chronos.domain.interactors.material.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.material.GetAllMateriaisInteractor;
import com.espweb.chronos.domain.model.Material;
import com.espweb.chronos.domain.repository.Repository;

import java.util.List;

public class GetAllMateriaisInteractorImpl extends AbstractInteractor implements GetAllMateriaisInteractor {
    private Repository<Material> materialRepository;
    private Callback callback;
    private long assuntoId;


    public GetAllMateriaisInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                         Callback callback,
                                         Repository<Material> materialRepository,
                                         long assuntoId) {
        super(threadExecutor, mainThread);

        if (materialRepository == null || callback == null) {
            throw new IllegalArgumentException("Argumentos nao podem ser nulos!");
        }

        this.materialRepository = materialRepository;
        this.callback = callback;
        this.assuntoId = assuntoId;
    }

    @Override
    public void run() {
        List<Material> materiais = materialRepository.getAll(assuntoId);
        if(materiais.size() > 0) {
            mainThread.post(() -> callback.onMateriaisRetrieved(materiais));
        } else {
            mainThread.post(() -> callback.onError("Erro ao listar materiais."));
        }
    }
}
