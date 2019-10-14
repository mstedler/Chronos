package com.espweb.chronos.presentation.presenters.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.sessao.SignInInteractor;
import com.espweb.chronos.domain.interactors.sessao.impl.SignInInteractorImpl;
import com.espweb.chronos.domain.model.User;
import com.espweb.chronos.domain.repository.SessaoRepository;
import com.espweb.chronos.presentation.converters.DomainToPresentationConverter;
import com.espweb.chronos.presentation.presenters.base.AbstractPresenter;
import com.espweb.chronos.presentation.presenters.SignInPresenter;
import com.espweb.chronos.presentation.utils.EmailValidator;
import com.espweb.chronos.presentation.viewmodels.UserViewModel;

public class SignInPresenterImpl extends AbstractPresenter implements SignInPresenter,
        SignInInteractor.Callback {

    private View view;
    private SessaoRepository sessaoRepository;
    private UserViewModel userViewModel;

    public SignInPresenterImpl(Executor executor, MainThread mainThread, View view, SessaoRepository sessaoRepository, UserViewModel userViewModel) {
        super(executor, mainThread);
        this.view = view;
        this.sessaoRepository = sessaoRepository;
        this.userViewModel = userViewModel;
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
        view.allowClick();
        view.hideProgress();
        view.showError(message);
    }

    @Override
    public void onSignInSuccess(User user) {
        userViewModel.setUser(DomainToPresentationConverter.convert(user));
        view.showWelcomeMessage(user);
        view.showMain();
    }

    @Override
    public void signInUser(String email, String password) {
        view.clearErrors();
        if(!EmailValidator.isValid(email)) {
            view.setEmailError();
            return;
        } else if(password.trim().length() < 6) {
            view.setPasswordError();
            return;
        }
        view.blockClick();
        view.showProgress();

        SignInInteractor signInInteractor = new SignInInteractorImpl(executor, mainThread, this, sessaoRepository,  email, password);
        signInInteractor.execute();
    }
}
