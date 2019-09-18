package com.espweb.chronos.domain.interactors.session.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.session.SignOutInteractor;
import com.espweb.chronos.domain.repository.SessaoRepository;

public class SignOutInteractorImpl extends AbstractInteractor implements SignOutInteractor {

    private Callback callback;
    private SessaoRepository sessaoRepository;

    public SignOutInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback, SessaoRepository sessaoRepository) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.sessaoRepository = sessaoRepository;
    }

    @Override
    public void run() {
        try {
            sessaoRepository.signOutUser();
            mainThread.post(() -> callback.onUserSignOut());
        } catch (Exception e) {
            mainThread.post(() -> callback.onError(e.getMessage()));
        }
    }
}
