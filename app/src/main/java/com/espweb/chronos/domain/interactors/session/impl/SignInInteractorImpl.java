package com.espweb.chronos.domain.interactors.login.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.login.SignInInteractor;
import com.espweb.chronos.domain.model.User;
import com.espweb.chronos.domain.repository.LoginRepository;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.domain.result.Result;
import com.espweb.chronos.domain.result.ResultStatus;

public class SignInInteractorImpl extends AbstractInteractor implements SignInInteractor {

    private Callback callback;
    private LoginRepository loginRepository;
    private Repository<User> userRepository;
    private String email;
    private String password;


    public SignInInteractorImpl(Executor threadExecutor,
                                MainThread mainThread,
                                Callback callback,
                                LoginRepository loginRepository,
                                Repository<User> userRepository, String email,
                                String password) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.loginRepository = loginRepository;
        this.userRepository = userRepository;
        this.email = email;
        this.password = password;
    }

    @Override
    public void run() {
        Result<User> result = loginRepository.signInUser(email, password);

        if(result.getStatus() == ResultStatus.SUCCESS) {
            userRepository.insert(0, result.get());
            mainThread.post(() -> callback.onSignInSuccess());
        } else {
            mainThread.post(() -> callback.onError(result.getErrorMessage()));
        }
    }
}
