package com.espweb.chronos.domain.interactors.session.impl;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.session.GetSessionUserInteractor;
import com.espweb.chronos.domain.model.User;
import com.espweb.chronos.domain.repository.SessaoRepository;

public class GetSessionUserInteractorImpl extends AbstractInteractor implements GetSessionUserInteractor {

    private Callback callback;
    private SessaoRepository sessaoRepository;

    public GetSessionUserInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback, SessaoRepository sessaoRepository) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.sessaoRepository = sessaoRepository;
    }

    @Override
    public void run() {
        try {
            User user = sessaoRepository.getUser();
            mainThread.post(() -> callback.onUserRetrieved(user));
        } catch (NotFoundException e) {
            mainThread.post(() -> callback.onUserNotFound());
        }
    }
}
