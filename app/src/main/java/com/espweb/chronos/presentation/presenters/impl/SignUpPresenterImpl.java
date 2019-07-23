package com.espweb.chronos.presentation.presenters.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.session.SignUpInteractor;
import com.espweb.chronos.domain.interactors.session.impl.SignUpInteractorImpl;
import com.espweb.chronos.domain.model.User;
import com.espweb.chronos.domain.repository.SessaoRepository;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.presentation.presenters.base.AbstractPresenter;
import com.espweb.chronos.presentation.presenters.SignUpPresenter;

public class SignUpPresenterImpl extends AbstractPresenter implements SignUpPresenter, SignUpInteractor.Callback {

    private View view;
    private SessaoRepository sessaoRepository;
    private Repository<User> userRepository;

    public SignUpPresenterImpl(Executor executor,
                               MainThread mainThread,
                               View view,
                               SessaoRepository sessaoRepository,
                               Repository<User> userRepository) {
        super(executor, mainThread);
        this.view = view;
        this.sessaoRepository = sessaoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onSignUpSuccess() {
        view.signUpSuccess();
    }

    @Override
    public void onError(String message) {
        view.signUpFailed(message);
    }

    @Override
    public void signUp(String name, String email, String password) {
        view.clearErrors();

        if(name.isEmpty()) {
            view.showNameError();
            return;
        } else if(email.isEmpty()){
            view.showEmailError();
            return;
        } else if(password.isEmpty()) {
            view.showPasswordError();
            return;
        }

        SignUpInteractor signUpInteractor = new SignUpInteractorImpl(executor, mainThread, this, sessaoRepository, name, email, password);
        signUpInteractor.execute();
    }
}
