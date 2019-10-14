package com.espweb.chronos.presentation.presenters.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.sessao.ResetPasswordInteractor;
import com.espweb.chronos.domain.interactors.sessao.impl.ResetPasswordInteractorImpl;
import com.espweb.chronos.domain.repository.SessaoRepository;
import com.espweb.chronos.presentation.presenters.base.AbstractPresenter;
import com.espweb.chronos.presentation.presenters.ResetPasswordPresenter;
import com.espweb.chronos.presentation.utils.EmailValidator;

public class ResetPasswordPresenterImpl extends AbstractPresenter implements
        ResetPasswordPresenter,
        ResetPasswordInteractor.Callback {

    private View view;
    private SessaoRepository sessaoRepository;

    public ResetPasswordPresenterImpl(Executor executor, MainThread mainThread, View view, SessaoRepository sessaoRepository) {
        super(executor, mainThread);
        this.view = view;
        this.sessaoRepository = sessaoRepository;
    }

    @Override
    public void resetPassword(String email) {
        if(!EmailValidator.isValid(email)) {
            view.onInvalidEmail();
            return;
        }
        view.showProgress();
        ResetPasswordInteractor resetPasswordInteractor = new ResetPasswordInteractorImpl(executor, mainThread, this, sessaoRepository, email);
        resetPasswordInteractor.execute();
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
    }

    @Override
    public void onRequestSent() {
        view.hideProgress();
        view.onRequestSent();
    }
}
