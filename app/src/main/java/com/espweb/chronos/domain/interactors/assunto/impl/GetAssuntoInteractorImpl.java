package com.espweb.chronos.domain.interactors.assunto.impl;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.assunto.GetAssuntoInteractor;
import com.espweb.chronos.domain.model.Assunto;
import com.espweb.chronos.domain.repository.Repository;

public class GetAssuntoInteractorImpl extends AbstractInteractor implements GetAssuntoInteractor {

    private Callback callback;
    private Repository<Assunto> assuntoRepository;
    private long id;

    public GetAssuntoInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback, Repository<Assunto> assuntoRepository, long id) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.assuntoRepository = assuntoRepository;
        this.id = id;
    }

    @Override
    public void run() {
       Assunto assunto = assuntoRepository.get(id);
       if(assunto != null) {
           mainThread.post(() -> callback.onAssuntoRetrieved(assunto));
       } else {
           mainThread.post(() -> callback.onAssuntoNotFound());
       }
    }
}
