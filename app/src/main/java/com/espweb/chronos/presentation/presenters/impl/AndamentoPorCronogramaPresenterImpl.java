package com.espweb.chronos.presentation.presenters.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.cronograma.GetAllCronogramasInteractor;
import com.espweb.chronos.domain.interactors.cronograma.impl.GetAllCronogramasInteractorImpl;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.repository.CronogramaRepository;
import com.espweb.chronos.presentation.converters.DomainToPresentationConverter;
import com.espweb.chronos.presentation.presenters.base.AbstractPresenter;
import com.espweb.chronos.presentation.presenters.AndamentoPorCronogramaPresenter;

import java.util.List;

public class AndamentoPorCronogramaPresenterImpl extends AbstractPresenter implements
        AndamentoPorCronogramaPresenter,
        GetAllCronogramasInteractor.Callback {

    private View view;
    private CronogramaRepository cronogramaRepository;

    public AndamentoPorCronogramaPresenterImpl(Executor executor, MainThread mainThread, View view, CronogramaRepository cronogramaRepository) {
        super(executor, mainThread);
        this.view = view;
        this.cronogramaRepository = cronogramaRepository;
    }

    @Override
    public void getAllCronogramas(long userId, boolean fromWeb) {
        GetAllCronogramasInteractor getAllCronogramasInteractor = new GetAllCronogramasInteractorImpl(executor, mainThread, this, cronogramaRepository, userId, fromWeb);
        getAllCronogramasInteractor.execute();
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
    public void onCronogramasRetrieved(List<Cronograma> cronogramas) {
        view.setCronogramas(DomainToPresentationConverter.convertCronogramas(cronogramas));
        view.showCharts();
    }

    @Override
    public void onCronogramasNotFound() {
        view.showEmptyView();
    }

    @Override
    public void onError(String message) {

    }
}
