package com.espweb.chronos.domain.interactors.revisao.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.revisao.CreateRevisaoInteractor;
import com.espweb.chronos.domain.interactors.revisao.UpdateRevisaoInteractor;
import com.espweb.chronos.domain.model.Revisao;
import com.espweb.chronos.domain.repository.Repository;

public class UpdateRevisaoInteractorImpl extends AbstractInteractor implements UpdateRevisaoInteractor {

    private Callback callback;
    private Repository<Revisao> revisaoRepository;
    private Revisao revisao;

    public UpdateRevisaoInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback, Repository<Revisao> revisaoRepository, Revisao revisao) {
        super(threadExecutor, mainThread);
        if (revisaoRepository == null || callback == null) {
            throw new IllegalArgumentException("Argumentos nao podem ser nulos!");
        }
        this.callback = callback;
        this.revisaoRepository = revisaoRepository;
        this.revisao = revisao;
    }

    @Override
    public void run() {
        boolean success = revisaoRepository.update(revisao);
        if(success) {
            mainThread.post(() -> callback.onRevisaoUpdated());
        } else {
            mainThread.post(() -> callback.onError("Erro ao atualizar revisao."));
        }
    }
}
