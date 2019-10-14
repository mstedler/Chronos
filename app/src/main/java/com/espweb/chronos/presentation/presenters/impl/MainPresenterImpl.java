package com.espweb.chronos.presentation.presenters.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.cronograma.GetAllCronogramasInteractor;
import com.espweb.chronos.domain.interactors.cronograma.impl.GetAllCronogramasInteractorImpl;
import com.espweb.chronos.domain.interactors.sessao.GetActiveUserInteractor;
import com.espweb.chronos.domain.interactors.sessao.impl.GetActiveUserInteractorImpl;
import com.espweb.chronos.domain.model.User;
import com.espweb.chronos.domain.repository.CronogramaRepository;
import com.espweb.chronos.domain.repository.SessaoRepository;
import com.espweb.chronos.presentation.converters.DomainToPresentationConverter;
import com.espweb.chronos.presentation.model.Cronograma;
import com.espweb.chronos.presentation.presenters.base.AbstractPresenter;
import com.espweb.chronos.presentation.presenters.MainPresenter;
import com.espweb.chronos.presentation.viewmodels.UserViewModel;

import java.util.Collections;
import java.util.List;

public class MainPresenterImpl extends AbstractPresenter implements MainPresenter,
        GetAllCronogramasInteractor.Callback,
        GetActiveUserInteractor.Callback {

    private MainPresenter.View view;
    private CronogramaRepository cronogramaRepository;
    private SessaoRepository sessaoRepository;

    public MainPresenterImpl(Executor executor,
                             MainThread mainThread,
                             View view,
                             CronogramaRepository cronogramaRepository,
                             SessaoRepository sessaoRepository) {
        super(executor, mainThread);

        this.sessaoRepository = sessaoRepository;
        this.cronogramaRepository = cronogramaRepository;
        this.view = view;
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
    public void onUserRetrieved(User user) {
        com.espweb.chronos.presentation.model.User pUser = DomainToPresentationConverter.convert(user);
    }

    @Override
    public void onUserNotFound() {
        view.navigateToLogin();
    }

    @Override
    public void onError(String message) {
        view.showError(message);
    }

    @Override
    public void onCronogramasRetrieved(List<com.espweb.chronos.domain.model.Cronograma> cronogramas) {
        List<Cronograma> cronogramass = DomainToPresentationConverter.convertCronogramas(cronogramas);
        //userViewModel.setCronogramas(cronogramass);
        view.hideProgress();
        view.showCronogramas(cronogramass);
        view.showRecyclerView();
    }

    @Override
    public void onCronogramasNotFound() {
        view.hideProgress();
        view.showEmptyView();
        //userViewModel.setCronogramas(Collections.emptyList());
    }


    @Override
    public void getUser() {
        GetActiveUserInteractor getActiveUserInteractor = new GetActiveUserInteractorImpl(executor,
                mainThread,
                this,
                sessaoRepository);
        getActiveUserInteractor.execute();
    }

    @Override
    public void getAllCronogramas(long userId, boolean fromWeb) {
        view.showProgress();
        GetAllCronogramasInteractor getAllCronogramasInteractor =
                new GetAllCronogramasInteractorImpl(executor, mainThread, this, cronogramaRepository, userId, fromWeb);
        getAllCronogramasInteractor.execute();
    }
}
