package com.espweb.chronos.domain.interactors.user.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.user.CreateUserInteractor;
import com.espweb.chronos.domain.model.User;
import com.espweb.chronos.domain.repository.SessionRepository;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.domain.result.Result;
import com.espweb.chronos.domain.result.ResultStatus;

public class CreateUserInteractorImpl extends AbstractInteractor implements CreateUserInteractor {

    private Callback callback;
    private SessionRepository sessionRepository;
    private String password;
    private String name;
    private String email;

    public CreateUserInteractorImpl(Executor threadExecutor,
                                    MainThread mainThread,
                                    Callback callback,
                                    SessionRepository sessionRepository,
                                    String name, String email, String password) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.sessionRepository = sessionRepository;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public void run() {
        Result<Boolean> result = sessionRepository.signUpUser(name, email, password);
        if(result.getStatus() == ResultStatus.SUCCESS){
            mainThread.post(() -> callback.onSignUpSuccess());
        } else {
            mainThread.post(() -> callback.onError("Erro ao criar usuário"));
        }
    }
}
