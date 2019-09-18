package com.espweb.chronos.presentation.presenters.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.sessao.GetSessaoInteractor;
import com.espweb.chronos.domain.interactors.sessao.impl.GetSessaoInteractorImpl;
import com.espweb.chronos.domain.model.Sessao;
import com.espweb.chronos.domain.repository.SessaoRepository;
import com.espweb.chronos.presentation.converters.DomainToPresentationConverter;
import com.espweb.chronos.presentation.model.User;
import com.espweb.chronos.presentation.presenters.base.AbstractPresenter;
import com.espweb.chronos.presentation.presenters.SplashPresenter;
import com.espweb.chronos.presentation.viewmodels.MainViewModel;

public class SplashPresenterImpl extends AbstractPresenter implements SplashPresenter, GetSessaoInteractor.Callback {

    private View view;
    private SessaoRepository sessaoRepository;

    private MainViewModel mainViewModel;


    public SplashPresenterImpl(Executor executor, MainThread mainThread, View view, SessaoRepository sessaoRepository, MainViewModel mainViewModel) {
        super(executor, mainThread);
        this.view = view;
        this.sessaoRepository = sessaoRepository;
        this.mainViewModel = mainViewModel;
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
    public void getSessao() {
        GetSessaoInteractor getSessaoInteractor = new GetSessaoInteractorImpl(executor, mainThread, this, sessaoRepository);
        getSessaoInteractor.execute();
    }

    @Override
    public void onSessaoRetrieved(Sessao sessao) {
        final User user = DomainToPresentationConverter.convert(sessao.getUser());
        mainViewModel.setUser(user);
        view.navigateToMain();
    }

    @Override
    public void onSessaoNotFound() {
        view.navigateToLogin();
    }
}
