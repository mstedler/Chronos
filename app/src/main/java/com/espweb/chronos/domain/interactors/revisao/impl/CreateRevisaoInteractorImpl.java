package com.espweb.chronos.domain.interactors.revisao.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.revisao.CreateRevisaoInteractor;
import com.espweb.chronos.domain.model.Revisao;
import com.espweb.chronos.domain.repository.Repository;

public class CreateRevisaoInteractorImpl extends AbstractInteractor implements CreateRevisaoInteractor {

    private Callback callback;
    private Repository<Revisao> revisaoRepository;
    private long assuntoId;
    private Revisao revisao;

    public CreateRevisaoInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                       Callback callback,
                                       Repository<Revisao> revisaoRepository,
                                       Revisao revisao,
                                       long assuntoId) {
        super(threadExecutor, mainThread);
        if (revisaoRepository == null || callback == null) {
            throw new IllegalArgumentException("Argumentos nao podem ser nulos!");
        }

        this.revisaoRepository = revisaoRepository;
        this.callback = callback;
        this.revisao = revisao;
        this.assuntoId = assuntoId;
    }

    @Override
    public void run() {
        long id = revisaoRepository.insert(assuntoId, revisao);
        if(id != 0) {
            mainThread.post(() -> callback.onRevisaoCreated(id));
        } else {
            mainThread.post(() -> callback.onError("Erro ao criar cronograma"));
        }
    }
}
