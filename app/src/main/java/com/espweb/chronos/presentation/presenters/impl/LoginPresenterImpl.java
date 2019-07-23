package com.espweb.chronos.presentation.presenters.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.repository.SessaoRepository;
import com.espweb.chronos.presentation.presenters.base.AbstractPresenter;
import com.espweb.chronos.presentation.presenters.LoginPresenter;

public class LoginPresenterImpl extends AbstractPresenter implements LoginPresenter {

    private View view;
    private SessaoRepository sessaoRepository;

    public LoginPresenterImpl(Executor executor, MainThread mainThread, View view, SessaoRepository sessaoRepository) {
        super(executor, mainThread);
        this.view = view;
        this.sessaoRepository = sessaoRepository;
    }

    @Override
    public void resume() {
        view.showSignInView();
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

    }

    @Override
    public void showSignIn() {
        view.showSignInView();
    }

    @Override
    public void showSignUp() {
        view.showSignUpView();
    }
}
