package com.espweb.chronos.domain.interactors.sessao.impl;

import com.espweb.chronos.domain.exceptions.SignInFailedException;
import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.sessao.SignInInteractor;
import com.espweb.chronos.domain.model.Sessao;
import com.espweb.chronos.domain.model.User;
import com.espweb.chronos.domain.repository.SessaoRepository;

public class SignInInteractorImpl extends AbstractInteractor implements SignInInteractor {

    private Callback callback;
    private SessaoRepository sessaoRepository;
    private String email;
    private String password;


    public SignInInteractorImpl(Executor threadExecutor,
                                MainThread mainThread,
                                Callback callback,
                                SessaoRepository sessaoRepository,
                                String email,
                                String password) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.sessaoRepository = sessaoRepository;
        this.email = email;
        this.password = password;
    }

    @Override
    public void run() {
        User user = new User(email, password);
        try {
            User user1 = sessaoRepository.signInUser(user);
            mainThread.post(() -> callback.onSignInSuccess(user1));
        } catch (SignInFailedException e) {
            mainThread.post(() -> callback.onError(e.getMessage()));
        }
    }
}
