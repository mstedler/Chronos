package com.espweb.chronos.presentation.presenters.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.artefato.CreateArtefatoInteractor;
import com.espweb.chronos.domain.interactors.artefato.UpdateArtefatoInteractor;
import com.espweb.chronos.domain.interactors.artefato.impl.CreateArtefatoInteractorImpl;
import com.espweb.chronos.domain.interactors.artefato.impl.UpdateArtefatoInteractorImpl;
import com.espweb.chronos.domain.repository.ArtefatoRepository;
import com.espweb.chronos.presentation.converters.DomainToPresentationConverter;
import com.espweb.chronos.presentation.converters.PresentationToDomainConverter;
import com.espweb.chronos.presentation.model.Artefato;
import com.espweb.chronos.presentation.presenters.ArtefatoDialogPresenter;
import com.espweb.chronos.presentation.presenters.base.AbstractPresenter;

public class ArtefatoDialogPresenterImpl extends AbstractPresenter implements ArtefatoDialogPresenter, CreateArtefatoInteractor.Callback, UpdateArtefatoInteractor.Callback {

    private View view;
    private ArtefatoRepository artefatoRepository;

    public ArtefatoDialogPresenterImpl(Executor executor, MainThread mainThread, View view, ArtefatoRepository artefatoRepository) {
        super(executor, mainThread);
        this.view = view;
        this.artefatoRepository = artefatoRepository;
    }

    @Override
    public void createArtefato(long assuntoId, Artefato artefato) {
        com.espweb.chronos.domain.model.Artefato dArtefato = PresentationToDomainConverter.convert(artefato);
        CreateArtefatoInteractor createArtefatoInteractor = new CreateArtefatoInteractorImpl(
                executor,
                mainThread,
                this,
                artefatoRepository,
                dArtefato,
                assuntoId
        );
        createArtefatoInteractor.execute();
    }

    @Override
    public void updateArtefato(Artefato artefato) {
        com.espweb.chronos.domain.model.Artefato dArtefato = PresentationToDomainConverter.convert(artefato);
        UpdateArtefatoInteractor updateArtefatoInteractor = new UpdateArtefatoInteractorImpl(
                executor,
                mainThread,
                this,
                artefatoRepository,
                dArtefato
        );
        updateArtefatoInteractor.execute();
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
    public void onArtefatoCreated(com.espweb.chronos.domain.model.Artefato artefato) {
        Artefato pArtefato = DomainToPresentationConverter.convert(artefato);
        view.onArtefatoCreated(pArtefato);
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onArtefatoUpdated() {
        view.onArtefatoUpdated();
    }
}
