package com.espweb.chronos.presentation.presenters.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.session.GetActiveUserInteractor;
import com.espweb.chronos.domain.interactors.session.impl.GetActiveUserInteractorImpl;
import com.espweb.chronos.domain.model.User;
import com.espweb.chronos.domain.repository.SessaoRepository;
import com.espweb.chronos.presentation.presenters.base.AbstractPresenter;
import com.espweb.chronos.presentation.presenters.SplashPresenter;

public class SplashPresenterImpl extends AbstractPresenter implements SplashPresenter, GetActiveUserInteractor.Callback {

    private View view;
    private SessaoRepository sessaoRepository;


    public SplashPresenterImpl(Executor executor, MainThread mainThread, View view, SessaoRepository sessaoRepository) {
        super(executor, mainThread);
        this.view = view;
        this.sessaoRepository = sessaoRepository;
    }

    @Override
    public void checkSessao() {
        GetActiveUserInteractor getActiveUserInteractor = new GetActiveUserInteractorImpl(
                executor,
                mainThread,
                this,
                sessaoRepository);
        getActiveUserInteractor.execute();
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

    }

    @Override
    public void onUserRetrieved(User user) {
        view.navigateToMain();
    }

    @Override
    public void onUserNotFound() {
        view.navigateToLogin();
    }
}
