package com.espweb.chronos.domain.interactors.sessao.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.sessao.ResetPasswordInteractor;
import com.espweb.chronos.domain.repository.SessaoRepository;

public class ResetPasswordInteractorImpl extends AbstractInteractor implements ResetPasswordInteractor {

    private Callback callback;
    private SessaoRepository sessaoRepository;
    private String email;

    public ResetPasswordInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback, SessaoRepository sessaoRepository, String email) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.sessaoRepository = sessaoRepository;
        this.email = email;
    }

    @Override
    public void run() {
        try {
            sessaoRepository.resetPassword(email);
            mainThread.post(() -> callback.onRequestSent());
        } catch (Exception e){
            mainThread.post(() -> callback.onError(e.getMessage()));
        }
    }
}
