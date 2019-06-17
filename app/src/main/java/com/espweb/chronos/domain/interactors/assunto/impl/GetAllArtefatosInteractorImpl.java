package com.espweb.chronos.domain.interactors.assunto.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.assunto.GetAllArtefatosInteractor;
import com.espweb.chronos.domain.model.Artefato;
import com.espweb.chronos.domain.repository.AssuntoRepository;

import java.util.List;

public class GetAllArtefatosInteractorImpl extends AbstractInteractor implements GetAllArtefatosInteractor {

    private AssuntoRepository assuntoRepository;
    private Callback callback;
    private long assuntoId;

    public GetAllArtefatosInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                         Callback callback,
                                         AssuntoRepository assuntoRepository,
                                         long assuntoId) {
        super(threadExecutor, mainThread);
        if (assuntoRepository == null || callback == null) {
            throw new IllegalArgumentException("Argumentos nao podem ser nulos!");
        }

        this.callback = callback;
        this.assuntoRepository = assuntoRepository;
        this.assuntoId = assuntoId;
    }

    @Override
    public void run() {
        List<Artefato> artefatos = assuntoRepository.getAllArtefatos(assuntoId);

        if(artefatos.size() > 0) {
            mainThread.post(() -> callback.onArtefatosRetrieved(artefatos));
        } else {
            mainThread.post(() -> callback.onError("Não há artefatos."));
        }
    }
}
