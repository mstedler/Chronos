package com.espweb.chronos.domain.interactors.assunto.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.assunto.UpdateAssuntoInteractor;
import com.espweb.chronos.domain.model.Assunto;
import com.espweb.chronos.domain.repository.Repository;

public class UpdateAssuntoInteractorImpl extends AbstractInteractor implements UpdateAssuntoInteractor {

    private Repository<Assunto> assuntoRepository;
    private Callback callback;
    private Assunto assunto;

    public UpdateAssuntoInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                       Callback callback,
                                       Repository<Assunto> assuntoRepository,
                                       Assunto assunto) {
        super(threadExecutor, mainThread);
        if (assuntoRepository == null || callback == null) {
            throw new IllegalArgumentException("Argumentos nao podem ser nulos!");
        }

        this.callback = callback;
        this.assuntoRepository = assuntoRepository;
        this.assunto = assunto;
    }

    @Override
    public void run() {
        assuntoRepository.update(assunto);
        mainThread.post(() -> callback.onAssuntoUpdated());
    }
}
