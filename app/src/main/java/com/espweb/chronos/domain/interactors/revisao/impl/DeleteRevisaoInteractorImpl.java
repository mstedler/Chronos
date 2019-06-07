package com.espweb.chronos.domain.interactors.revisao.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.revisao.DeleteRevisaoInteractor;
import com.espweb.chronos.domain.model.Revisao;
import com.espweb.chronos.domain.repository.Repository;

public class DeleteRevisaoInteractorImpl extends AbstractInteractor implements DeleteRevisaoInteractor {

    private Callback callback;
    private Repository<Revisao> revisaoRepository;
    private long revisaoId;

    public DeleteRevisaoInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback, Repository<Revisao> revisaoRepository, long revisaoId) {
        super(threadExecutor, mainThread);
        if (revisaoRepository == null || callback == null) {
            throw new IllegalArgumentException("Argumentos nao podem ser nulos!");
        }
        this.callback = callback;
        this.revisaoRepository = revisaoRepository;
        this.revisaoId = revisaoId;
    }

    @Override
    public void run() {
        boolean success = revisaoRepository.delete(revisaoId);

        if(success) {
            mainThread.post(() -> callback.onRevisaoDeleted());
        } else {
            mainThread.post(() -> callback.onError("Erro ao excluir revisao."));
        }
    }
}
