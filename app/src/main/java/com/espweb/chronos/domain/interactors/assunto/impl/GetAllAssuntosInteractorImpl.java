package com.espweb.chronos.domain.interactors.assunto.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.assunto.GetAllAssuntosInteractor;
import com.espweb.chronos.domain.model.Assunto;
import com.espweb.chronos.domain.repository.AssuntoRepository;
import com.espweb.chronos.domain.repository.Repository;

import java.util.List;

public class GetAllAssuntosInteractorImpl extends AbstractInteractor implements GetAllAssuntosInteractor {

    private AssuntoRepository assuntoRepository;
    private Callback callback;
    private long disciplinaId;
    public GetAllAssuntosInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                        Callback callback,
                                        AssuntoRepository assuntoRepository,
                                        long disciplinaId) {
        super(threadExecutor, mainThread);
        if (assuntoRepository == null || callback == null) {
            throw new IllegalArgumentException("Argumentos nao podem ser nulos!");
        }

        this.callback = callback;
        this.assuntoRepository = assuntoRepository;
        this.disciplinaId = disciplinaId;
    }

    @Override
    public void run() {
        List<Assunto> assuntos = assuntoRepository.getAll(disciplinaId);

        if(assuntos.size() > 0) {
            mainThread.post(() -> callback.onAssuntosRetrieved(assuntos));
        } else {
            mainThread.post(() -> callback.onError("Não há assuntos."));
        }
    }
}
