package com.espweb.chronos.presentation.presenters.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.session.SignInInteractor;
import com.espweb.chronos.domain.interactors.session.impl.SignInInteractorImpl;
import com.espweb.chronos.domain.model.User;
import com.espweb.chronos.domain.repository.SessaoRepository;
import com.espweb.chronos.presentation.presenters.base.AbstractPresenter;
import com.espweb.chronos.presentation.presenters.SignInPresenter;

public class SignInPresenterImpl extends AbstractPresenter implements SignInPresenter,
        SignInInteractor.Callback {

    private View view;
    private SessaoRepository sessaoRepository;

    public SignInPresenterImpl(Executor executor, MainThread mainThread, View view, SessaoRepository sessaoRepository) {
        super(executor, mainThread);
        this.view = view;
        this.sessaoRepository = sessaoRepository;
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
    public void onError(String message) {
        view.hideProgress();
        view.showError(message);
    }

    @Override
    public void onSignInSuccess(User user) {
        view.hideProgress();
        view.showWelcomeMessage(user);
        view.showMainActivity();
    }

    @Override
    public void signInUser(String email, String password) {
        view.clearErrors();
        if(email.isEmpty()) {
            view.setEmailError();
            return;
        } else if(password.isEmpty()) {
            view.setPasswordError();
            return;
        }

        view.showProgress();

        SignInInteractor signInInteractor = new SignInInteractorImpl(executor, mainThread, this, sessaoRepository,  email, password);
        signInInteractor.execute();
    }
}
