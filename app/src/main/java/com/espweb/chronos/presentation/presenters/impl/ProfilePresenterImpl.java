package com.espweb.chronos.presentation.presenters.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.sessao.SignOutInteractor;
import com.espweb.chronos.domain.interactors.sessao.impl.SignOutInteractorImpl;
import com.espweb.chronos.domain.repository.SessaoRepository;
import com.espweb.chronos.presentation.presenters.ProfilePresenter;
import com.espweb.chronos.presentation.presenters.base.AbstractPresenter;

public class ProfilePresenterImpl extends AbstractPresenter implements ProfilePresenter, SignOutInteractor.Callback {

    private View view;
    private SessaoRepository sessaoRepository;

    public ProfilePresenterImpl(Executor executor, MainThread mainThread, View view, SessaoRepository sessaoRepository) {
        super(executor, mainThread);
        this.view = view;
        this.sessaoRepository = sessaoRepository;
    }

    @Override
    public void logout() {
        SignOutInteractor signOutInteractor = new SignOutInteractorImpl(executor, mainThread, this, sessaoRepository);
        signOutInteractor.execute();
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
    public void onUserSignOut() {
        view.showLoginView();
    }

    @Override
    public void onError(String message) {

    }
}
