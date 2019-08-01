package com.espweb.chronos.presentation.presenters.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.cronograma.CreateCronogramaInteractor;
import com.espweb.chronos.domain.interactors.cronograma.UpdateCronogramaInteractor;
import com.espweb.chronos.domain.interactors.cronograma.impl.CreateCronogramaInteractorImpl;
import com.espweb.chronos.domain.interactors.cronograma.impl.UpdateCronogramaInteractorImpl;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.presentation.converters.DomainToPresentationConverter;
import com.espweb.chronos.presentation.converters.PresentationToDomainConverter;
import com.espweb.chronos.presentation.model.Cronograma;
import com.espweb.chronos.presentation.presenters.base.AbstractPresenter;
import com.espweb.chronos.presentation.presenters.CronogramaDialogPresenter;

public class CronogramaDialogPresenterImpl extends AbstractPresenter implements CronogramaDialogPresenter, UpdateCronogramaInteractor.Callback, CreateCronogramaInteractor.Callback {

    private View view;
    private Repository<com.espweb.chronos.domain.model.Cronograma> cronogramaRepository;

    public CronogramaDialogPresenterImpl(Executor executor, MainThread mainThread, View view, Repository<com.espweb.chronos.domain.model.Cronograma> cronogramaRepository) {
        super(executor, mainThread);
        this.view = view;
        this.cronogramaRepository = cronogramaRepository;
    }

    @Override
    public void createCronograma(Cronograma cronograma) {
        view.showProgress();
        com.espweb.chronos.domain.model.Cronograma dCronograma = PresentationToDomainConverter.convert(cronograma);
        CreateCronogramaInteractor createCronogramaInteractor = new CreateCronogramaInteractorImpl(
                executor,
                mainThread,
                this,
                cronogramaRepository,
                dCronograma
        );
        createCronogramaInteractor.execute();
    }

    @Override
    public void updateCronograma(Cronograma cronograma) {
        view.showProgress();
        com.espweb.chronos.domain.model.Cronograma dCronograma = PresentationToDomainConverter.convert(cronograma);
        UpdateCronogramaInteractor updateCronogramaInteractor = new UpdateCronogramaInteractorImpl(
                executor,
                mainThread,
                this,
                cronogramaRepository,
                dCronograma
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
    public void onCronogramaCreated(com.espweb.chronos.domain.model.Cronograma cronograma) {
        view.hideProgress();
        Cronograma pCronograma = DomainToPresentationConverter.convert(cronograma);
        view.cronogramaCreated(pCronograma);
        view.dismissDialog();
    }

    @Override
    public void onError(String message) {
        view.hideProgress();

    }
}
