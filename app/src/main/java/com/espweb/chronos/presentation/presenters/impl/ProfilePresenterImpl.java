package com.espweb.chronos.presentation.presenters.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.cronograma.GetCronogramaCountInteractor;
import com.espweb.chronos.domain.interactors.cronograma.impl.GetCronogramaCountInteractorImpl;
import com.espweb.chronos.domain.interactors.sessao.SignOutInteractor;
import com.espweb.chronos.domain.interactors.sessao.impl.SignOutInteractorImpl;
import com.espweb.chronos.domain.repository.CronogramaRepository;
import com.espweb.chronos.domain.repository.SessaoRepository;
import com.espweb.chronos.presentation.presenters.ProfilePresenter;
import com.espweb.chronos.presentation.presenters.base.AbstractPresenter;

public class ProfilePresenterImpl extends AbstractPresenter implements
        ProfilePresenter,
        SignOutInteractor.Callback,
        GetCronogramaCountInteractor.Callback {

    private View view;
    private SessaoRepository sessaoRepository;
    private CronogramaRepository cronogramaRepository;

    public ProfilePresenterImpl(Executor executor, MainThread mainThread, View view, SessaoRepository sessaoRepository, CronogramaRepository cronogramaRepository) {
        super(executor, mainThread);
        this.view = view;
        this.sessaoRepository = sessaoRepository;
        this.cronogramaRepository = cronogramaRepository;
    }

    @Override
    public void logout() {
        SignOutInteractor signOutInteractor = new SignOutInteractorImpl(executor, mainThread, this, sessaoRepository);
        signOutInteractor.execute();
    }

    @Override
    public void getCronogramaCount(long userId) {
        GetCronogramaCountInteractor getCronogramaCountInteractor = new GetCronogramaCountInteractorImpl(executor, mainThread, this, cronogramaRepository, userId);
        getCronogramaCountInteractor.execute();
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
    public void onCounted(int count) {
        view.setCronogramaCount(count);
    }

    @Override
    public void onError(String message) {

    }
}
