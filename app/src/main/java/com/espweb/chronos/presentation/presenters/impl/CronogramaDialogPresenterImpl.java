package com.espweb.chronos.presentation.presenters.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.cronograma.CreateCronogramaInteractor;
import com.espweb.chronos.domain.interactors.cronograma.UpdateCronogramaInteractor;
import com.espweb.chronos.domain.interactors.cronograma.impl.CreateCronogramaInteractorImpl;
import com.espweb.chronos.domain.interactors.cronograma.impl.UpdateCronogramaInteractorImpl;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.presentation.presenters.base.AbstractPresenter;
import com.espweb.chronos.presentation.presenters.CronogramaDialogPresenter;

public class CronogramaDialogPresenterImpl extends AbstractPresenter implements CronogramaDialogPresenter, UpdateCronogramaInteractor.Callback, CreateCronogramaInteractor.Callback {

    private View view;
    private Repository<Cronograma> cronogramaRepository;

    public CronogramaDialogPresenterImpl(Executor executor, MainThread mainThread, View view, Repository<Cronograma> cronogramaRepository) {
        super(executor, mainThread);
        this.view = view;
        this.cronogramaRepository = cronogramaRepository;
    }

    @Override
    public void createCronograma(long userId, Cronograma cronograma) {
        view.showProgress();
        CreateCronogramaInteractor createCronogramaInteractor = new CreateCronogramaInteractorImpl(
                executor,
                mainThread,
                this,
                cronogramaRepository,
                cronograma,
                userId
        );
        createCronogramaInteractor.execute();
    }

    @Override
    public void updateCronograma(Cronograma cronograma) {
        view.showProgress();
        UpdateCronogramaInteractor updateCronogramaInteractor = new UpdateCronogramaInteractorImpl(
                executor,
                mainThread,
                this,
                cronogramaRepository,
                cronograma
        );

        updateCronogramaInteractor.execute();
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
    public void onCronogramaUpdated() {
        view.hideProgress();
        view.cronogramaUpdated();
        view.dismissDialog();
    }

    @Override
    public void onCronogramaCreated(Cronograma cronograma) {
        view.hideProgress();
        view.cronogramaCreated(cronograma);
        view.dismissDialog();
    }

    @Override
    public void onError(String message) {
        view.hideProgress();

    }
}
