package com.espweb.chronos.domain.interactors.sessao.impl;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.sessao.GetSessaoInteractor;
import com.espweb.chronos.domain.model.Sessao;
import com.espweb.chronos.domain.repository.SessaoRepository;

public class GetSessaoInteractorImpl extends AbstractInteractor implements GetSessaoInteractor {

    private Callback callback;
    private SessaoRepository sessaoRepository;

    public GetSessaoInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback, SessaoRepository sessaoRepository) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.sessaoRepository = sessaoRepository;
    }

    @Override
    public void run() {
        try {
            Sessao sessao = sessaoRepository.getSessao();
            mainThread.post(() -> callback.onSessaoRetrieved(sessao));
        } catch (NotFoundException e) {
            mainThread.post(() -> callback.onSessaoNotFound());
        }
    }
}
