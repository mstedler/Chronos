package com.espweb.chronos.presentation.presenters.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.assunto.CreateAssuntoInteractor;
import com.espweb.chronos.domain.interactors.assunto.UpdateAssuntoInteractor;
import com.espweb.chronos.domain.interactors.assunto.impl.CreateAssuntoInteractorImpl;
import com.espweb.chronos.domain.interactors.assunto.impl.UpdateAssuntoInteractorImpl;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.presentation.converters.DomainToPresentationConverter;
import com.espweb.chronos.presentation.converters.PresentationToDomainConverter;
import com.espweb.chronos.presentation.model.Assunto;
import com.espweb.chronos.presentation.presenters.base.AbstractPresenter;
import com.espweb.chronos.presentation.presenters.AssuntoDialogPresenter;

public class AssuntoDialogPresenterImpl extends AbstractPresenter implements AssuntoDialogPresenter, CreateAssuntoInteractor.Callback, UpdateAssuntoInteractor.Callback {

    private View view;
    private Repository<com.espweb.chronos.domain.model.Assunto> assuntoRepository;

    public AssuntoDialogPresenterImpl(Executor executor, MainThread mainThread, View view, Repository<com.espweb.chronos.domain.model.Assunto> assuntoRepository) {
        super(executor, mainThread);
        this.view = view;
        this.assuntoRepository = assuntoRepository;
    }

    @Override
    public void createAssunto(Assunto assunto) {
        com.espweb.chronos.domain.model.Assunto dAssunto = PresentationToDomainConverter.convert(assunto);
        CreateAssuntoInteractor createAssuntoInteractor = new CreateAssuntoInteractorImpl(
                executor,
                mainThread,
                this,
                assuntoRepository,
                dAssunto);
        createAssuntoInteractor.execute();
    }

    @Override
    public void updateAssunto(Assunto assunto) {
        com.espweb.chronos.domain.model.Assunto dAssunto = PresentationToDomainConverter.convert(assunto);
        UpdateAssuntoInteractor updateAssuntoInteractor = new UpdateAssuntoInteractorImpl(
                executor,
                mainThread,
                this,
                assuntoRepository,
                dAssunto
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
    public void onAssuntoCreated(com.espweb.chronos.domain.model.Assunto assunto) {
        Assunto pAssunto = DomainToPresentationConverter.convert(assunto);
        view.onAssuntoCreated(pAssunto);
    }

    @Override
    public void onAssuntoUpdated() {
        view.onAssuntoUpdated();
    }

    @Override
    public void onError(String message) {

    }
}
