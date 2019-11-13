package com.espweb.chronos.presentation.presenters.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.sessao.SignUpInteractor;
import com.espweb.chronos.domain.interactors.sessao.impl.SignUpInteractorImpl;
import com.espweb.chronos.domain.model.User;
import com.espweb.chronos.domain.repository.SessaoRepository;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.presentation.presenters.base.AbstractPresenter;
import com.espweb.chronos.presentation.presenters.SignUpPresenter;
import com.espweb.chronos.presentation.utils.EmailValidator;

public class SignUpPresenterImpl extends AbstractPresenter implements SignUpPresenter, SignUpInteractor.Callback {

    private View view;
    private SessaoRepository sessaoRepository;

    public SignUpPresenterImpl(Executor executor,
                               MainThread mainThread,
                               View view,
                               SessaoRepository sessaoRepository) {
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
    public void onSignUpSuccess() {
        view.hideProgress();
        view.signUpSuccess();
    }

    @Override
    public void onError(String message) {
        view.hideProgress();
        view.signUpFailed(message);
    }

    @Override
    public void signUp(String name, String email, String password) {
        view.clearErrors();

        if(name.isEmpty()) {
            view.showNameError();
            return;
        } else if(!EmailValidator.isValid(email)){
            view.showEmailError();
            return;
        } else if(password.length() < 6) {
            view.showPasswordError();
            return;
        }

        view.showProgress();
        SignUpInteractor signUpInteractor = new SignUpInteractorImpl(executor, mainThread, this, sessaoRepository, name, email, password);
        signUpInteractor.execute();
    }
}
