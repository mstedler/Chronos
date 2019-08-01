package com.espweb.chronos.presentation.presenters.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.artefato.GetAllArtefatosInteractor;
import com.espweb.chronos.domain.interactors.artefato.impl.GetAllArtefatosInteractorImpl;
import com.espweb.chronos.domain.model.Artefato;
import com.espweb.chronos.domain.repository.ArtefatoRepository;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.presentation.converters.DomainToPresentationConverter;
import com.espweb.chronos.presentation.presenters.base.AbstractPresenter;
import com.espweb.chronos.presentation.presenters.AssuntoPresenter;

import java.util.List;

public class AssuntoPresenterImpl extends AbstractPresenter implements AssuntoPresenter, GetAllArtefatosInteractor.Callback {

    private View view;
    private ArtefatoRepository artefatoRepository;


    public AssuntoPresenterImpl(Executor executor, MainThread mainThread, View view, ArtefatoRepository artefatoRepository) {
        super(executor, mainThread);
        this.view = view;
        this.artefatoRepository = artefatoRepository;
    }

    @Override
    public void getAllArtefatos(long assuntoId) {
        GetAllArtefatosInteractor getAllArtefatosInteractor =
                new GetAllArtefatosInteractorImpl(executor, mainThread, this, artefatoRepository, assuntoId);

        getAllArtefatosInteractor.execute();
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
    public void onArtefatosRetrieved(List<Artefato> artefatos) {
        List<com.espweb.chronos.presentation.model.Artefato> pArtefatos = DomainToPresentationConverter.convertArtefatos(artefatos);
        view.showArtefatos(pArtefatos);
    }

    @Override
    public void onError(String message) {
        view.showError(message);
    }
}
