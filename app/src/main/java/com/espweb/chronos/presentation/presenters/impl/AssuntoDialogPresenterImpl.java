package com.espweb.chronos.presentation.presenters.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.assunto.CreateAssuntoInteractor;
import com.espweb.chronos.domain.interactors.assunto.UpdateAssuntoInteractor;
import com.espweb.chronos.domain.interactors.assunto.impl.CreateAssuntoInteractorImpl;
import com.espweb.chronos.domain.interactors.assunto.impl.UpdateAssuntoInteractorImpl;
import com.espweb.chronos.domain.model.Assunto;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.presentation.presenters.base.AbstractPresenter;
import com.espweb.chronos.presentation.presenters.AssuntoDialogPresenter;

public class AssuntoDialogPresenterImpl extends AbstractPresenter implements AssuntoDialogPresenter, CreateAssuntoInteractor.Callback, UpdateAssuntoInteractor.Callback {

    private View view;
    private Repository<Assunto> assuntoRepository;

    public AssuntoDialogPresenterImpl(Executor executor, MainThread mainThread, View view, Repository<Assunto> assuntoRepository) {
        super(executor, mainThread);
        this.view = view;
        this.assuntoRepository = assuntoRepository;
    }

    @Override
    public void createAssunto(long disciplinaId, Assunto assunto) {
        CreateAssuntoInteractor createAssuntoInteractor = new CreateAssuntoInteractorImpl(
                executor,
                mainThread,
                this,
                assuntoRepository,
                disciplinaId,
                assunto);
        createAssuntoInteractor.execute();
    }

    @Override
    public void updateAssunto(Assunto assunto) {
        UpdateAssuntoInteractor updateAssuntoInteractor = new UpdateAssuntoInteractorImpl(
                executor,
                mainThread,
                this,
                assuntoRepository,
                assunto
        );
        updateAssuntoInteractor.execute();
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
    public void onAssuntoCreated(Assunto assunto) {
        view.onAssuntoCreated(assunto);
    }

    @Override
    public void onAssuntoUpdated() {
        view.onAssuntoUpdated();
    }

    @Override
    public void onError(String message) {

    }
}
