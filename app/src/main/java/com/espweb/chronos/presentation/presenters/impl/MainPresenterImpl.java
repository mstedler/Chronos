package com.espweb.chronos.presentation.presenters.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.GetAllCronogramasInteractor;
import com.espweb.chronos.domain.interactors.impl.GetAllCronogramasInteractorImpl;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.repository.CronogramaRepository;
import com.espweb.chronos.presentation.presenters.base.AbstractPresenter;
import com.espweb.chronos.presentation.presenters.MainPresenter;

import java.util.List;

public class MainPresenterImpl extends AbstractPresenter implements MainPresenter,
        //Podemos usar varios interactors em um mesmo presenter!
        GetAllCronogramasInteractor.Callback {

    private MainPresenter.View view;
    private CronogramaRepository cronogramaRepository;
    
    public MainPresenterImpl(Executor executor,
                             MainThread mainThread,
                             View view,
                             CronogramaRepository cronogramaRepository) {
        super(executor, mainThread);

        this.cronogramaRepository = cronogramaRepository;
        this.view = view;
    }

    @Override
    public void resume() {
        getAllCronogramas();
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
        view.showError(message);
    }

    @Override
    public void onCronogramasRetrieved(List<Cronograma> cronogramas) {
        view.showCronogramas(cronogramas);
    }


    @Override
    public void getAllCronogramas() {
        GetAllCronogramasInteractor getAllCronogramasInteractor =
                new GetAllCronogramasInteractorImpl(executor, mainThread, this ,cronogramaRepository);

        getAllCronogramasInteractor.execute();
    }
}
