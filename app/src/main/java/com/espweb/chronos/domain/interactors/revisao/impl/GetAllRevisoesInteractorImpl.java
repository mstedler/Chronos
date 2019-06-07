package com.espweb.chronos.domain.interactors.revisao.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.revisao.GetAllRevisoesInteractor;
import com.espweb.chronos.domain.model.Revisao;
import com.espweb.chronos.domain.repository.Repository;

import java.util.List;

public class GetAllRevisoesInteractorImpl extends AbstractInteractor implements GetAllRevisoesInteractor {

    private Callback callback;
    private Repository<Revisao> revisaoRepository;
    private long assuntoId;

    public GetAllRevisoesInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback, Repository<Revisao> revisaoRepository, long assuntoId) {
        super(threadExecutor, mainThread);
        if (revisaoRepository == null || callback == null) {
            throw new IllegalArgumentException("Argumentos nao podem ser nulos!");
        }
        this.callback = callback;
        this.revisaoRepository = revisaoRepository;
        this.assuntoId = assuntoId;
    }

    @Override
    public void run() {
        List<Revisao> revisoes = revisaoRepository.getAll(assuntoId);
        if(revisoes.size() > 0) {
            mainThread.post(() -> callback.onRevisoesRetrieved(revisoes));
        } else {
            mainThread.post(() -> callback.onError("Erro ao listar revisoes"));
        }
    }
}
