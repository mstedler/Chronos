package com.espweb.chronos.domain.interactors.assunto.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.assunto.CreateAssuntoInteractor;
import com.espweb.chronos.domain.model.Assunto;
import com.espweb.chronos.domain.repository.Repository;

public class CreateAssuntoInteractorImpl extends AbstractInteractor implements CreateAssuntoInteractor {

    private Repository<Assunto> assuntoRepository;
    private Callback callback;
    private long disciplinaId;
    private Assunto assunto;

    public CreateAssuntoInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                       Callback callback,
                                       Repository<Assunto> assuntoRepository,
                                       long disciplinaId,
                                       Assunto assunto) {
        super(threadExecutor, mainThread);

        if (assuntoRepository == null || callback == null) {
            throw new IllegalArgumentException("Argumentos nao podem ser nulos!");
        }

        this.callback = callback;
        this.assuntoRepository = assuntoRepository;
        this.disciplinaId = disciplinaId;
        this.assunto = assunto;
    }

    @Override
    public void run() {
        try {
            assunto.setDisciplinaId(disciplinaId);
            long assuntoId = assuntoRepository.insert(assunto);
            assunto.setId(assuntoId);
            mainThread.post(() -> callback.onAssuntoCreated(assunto));
        } catch (Exception e) {
            mainThread.post(() -> callback.onError("Erro ao criar assunto."));
        }
    }
}
