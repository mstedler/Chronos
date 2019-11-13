package com.espweb.chronos.presentation.presenters.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.artefato.DeleteArtefatoInteractor;
import com.espweb.chronos.domain.interactors.artefato.GetAllArtefatosInteractor;
import com.espweb.chronos.domain.interactors.artefato.impl.DeleteArtefatoInteractorImpl;
import com.espweb.chronos.domain.interactors.artefato.impl.GetAllArtefatosInteractorImpl;
import com.espweb.chronos.domain.interactors.assunto.DeleteAssuntoInteractor;
import com.espweb.chronos.domain.interactors.assunto.GetAssuntoInteractor;
import com.espweb.chronos.domain.interactors.assunto.impl.DeleteAssuntoInteractorImpl;
import com.espweb.chronos.domain.interactors.assunto.impl.GetAssuntoInteractorImpl;
import com.espweb.chronos.domain.model.Artefato;
import com.espweb.chronos.domain.model.Assunto;
import com.espweb.chronos.domain.repository.ArtefatoRepository;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.presentation.converters.DomainToPresentationConverter;
import com.espweb.chronos.presentation.converters.PresentationToDomainConverter;
import com.espweb.chronos.presentation.presenters.base.AbstractPresenter;
import com.espweb.chronos.presentation.presenters.AssuntoPresenter;

import java.util.List;

public class AssuntoPresenterImpl extends AbstractPresenter implements AssuntoPresenter,
        GetAllArtefatosInteractor.Callback,
        DeleteAssuntoInteractor.Callback, GetAssuntoInteractor.Callback, DeleteArtefatoInteractor.Callback {

    private View view;
    private ArtefatoRepository artefatoRepository;
    private Repository<Assunto> assuntoRepository;

    public AssuntoPresenterImpl(Executor executor, MainThread mainThread, View view, ArtefatoRepository artefatoRepository, Repository<Assunto> assuntoRepository) {
        super(executor, mainThread);
        this.view = view;
        this.artefatoRepository = artefatoRepository;
        this.assuntoRepository = assuntoRepository;
    }

    @Override
    public void deleteAssunto(com.espweb.chronos.presentation.model.Assunto assunto) {
        DeleteAssuntoInteractor deleteAssuntoInteractor = new DeleteAssuntoInteractorImpl(executor, mainThread, this, assuntoRepository, PresentationToDomainConverter.convert(assunto));
        deleteAssuntoInteractor.execute();
    }

    @Override
    public void deleteArtefato(com.espweb.chronos.presentation.model.Artefato artefato) {
        DeleteArtefatoInteractor deleteArtefatoInteractor = new DeleteArtefatoInteractorImpl(executor, mainThread, this, artefatoRepository, PresentationToDomainConverter.convert(artefato));
        deleteArtefatoInteractor.execute();
    }

    @Override
    public void getAssunto(long id) {
        GetAssuntoInteractor getAssuntoInteractor = new GetAssuntoInteractorImpl(executor, mainThread, this, assuntoRepository, id);
        getAssuntoInteractor.execute();
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
    public void onArtefatoNotFound() {
        view.showEmptyView();
        view.onArtefatoNotFound();
    }

    @Override
    public void onAssuntoDeleted() {
        view.navigateToCronograma();
    }

    @Override
    public void onError(String message) {
    }

    @Override
    public void onAssuntoRetrieved(Assunto assunto) {
        com.espweb.chronos.presentation.model.Assunto pAssunto = DomainToPresentationConverter.convert(assunto);
        view.setAssunto(pAssunto);
        if(pAssunto.getArtefatos().isEmpty())
            view.showEmptyView();
        else
            view.showArtefatos(pAssunto.getArtefatos());
    }

    @Override
    public void onAssuntoNotFound() {

    }

    @Override
    public void onArtefatoDeleted() {
        view.onArtefatoDeleted();
    }

    @Override
    public void onArtefatoNotValid() {

    }
}
