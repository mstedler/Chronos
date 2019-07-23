package com.espweb.chronos.domain.interactors.artefato.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.artefato.GetAllArtefatosInteractor;
import com.espweb.chronos.domain.model.Artefato;
import com.espweb.chronos.domain.repository.ArtefatoRepository;
import com.espweb.chronos.domain.repository.Repository;

import java.util.List;

public class GetAllArtefatosInteractorImpl extends AbstractInteractor implements GetAllArtefatosInteractor {

    private ArtefatoRepository artefatoRepository;
    private Callback callback;
    private long assuntoId;

    public GetAllArtefatosInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                         Callback callback,
                                         ArtefatoRepository artefatoRepository,
                                         long assuntoId) {
        super(threadExecutor, mainThread);
        if (artefatoRepository == null || callback == null) {
            throw new IllegalArgumentException("Argumentos nao podem ser nulos!");
        }

        this.callback = callback;
        this.artefatoRepository = artefatoRepository;
        this.assuntoId = assuntoId;
    }

    @Override
    public void run() {
        List<Artefato> artefatos = artefatoRepository.getAll(assuntoId);

        if(artefatos.size() > 0) {
            mainThread.post(() -> callback.onArtefatosRetrieved(artefatos));
        } else {
            mainThread.post(() -> callback.onError("Não há artefatos."));
        }
    }
}
