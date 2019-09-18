package com.espweb.chronos.domain.interactors.session.impl;

import com.espweb.chronos.domain.exceptions.SignUpFailedException;
import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.session.SignUpInteractor;
import com.espweb.chronos.domain.model.User;
import com.espweb.chronos.domain.repository.SessaoRepository;

public class SignUpInteractorImpl extends AbstractInteractor implements SignUpInteractor {

    private Callback callback;
    private SessaoRepository sessaoRepository;
    private String password;
    private String name;
    private String email;

    public SignUpInteractorImpl(Executor threadExecutor,
                                MainThread mainThread,
                                Callback callback,
                                SessaoRepository sessaoRepository,
                                String name, String email, String password) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.sessaoRepository = sessaoRepository;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public void run() {
        User user = new User(name, email, password);
       try {
            sessaoRepository.signUpUser(user);
            mainThread.post(() -> callback.onSignUpSuccess());
        } catch (SignUpFailedException e){
            mainThread.post(() -> callback.onError(e.getMessage()));
        }
    }
}
