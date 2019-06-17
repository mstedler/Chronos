package com.espweb.chronos.domain.interactors.assunto.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.assunto.DeleteAssuntoInteractor;
import com.espweb.chronos.domain.model.Assunto;
import com.espweb.chronos.domain.repository.AssuntoRepository;
import com.espweb.chronos.domain.repository.Repository;

public class DeleteAssuntoInteractorImpl extends AbstractInteractor implements DeleteAssuntoInteractor {

    private AssuntoRepository assuntoRepository;
    private Callback callback;
    private long assuntoId;
    public DeleteAssuntoInteractorImpl(Executor threadExecutor, MainThread mainThread,
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
        boolean success = assuntoRepository.delete(assuntoId);

        if(success) {
            mainThread.post(() -> callback.onAssuntoDeleted());
        } else {
            mainThread.post(() -> callback.onError("Erro ao excluir assunto."));
        }
    }
}
