package com.espweb.chronos.presentation.presenters.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.disciplina.CreateDisciplinaInteractor;
import com.espweb.chronos.domain.interactors.disciplina.UpdateDisciplinaInteractor;
import com.espweb.chronos.domain.interactors.disciplina.impl.CreateDisciplinaInteractorImpl;
import com.espweb.chronos.domain.interactors.disciplina.impl.UpdateDisciplinaInteractorImpl;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.presentation.converters.DomainToPresentationConverter;
import com.espweb.chronos.presentation.converters.PresentationToDomainConverter;
import com.espweb.chronos.presentation.model.Disciplina;
import com.espweb.chronos.presentation.presenters.base.AbstractPresenter;
import com.espweb.chronos.presentation.presenters.DisciplinaDialogPresenter;

public class DisciplinaDialogPresenterImpl extends AbstractPresenter implements DisciplinaDialogPresenter, CreateDisciplinaInteractor.Callback, UpdateDisciplinaInteractor.Callback {

    private View view;
    private Repository<com.espweb.chronos.domain.model.Disciplina> disciplinaRepository;

    public DisciplinaDialogPresenterImpl(Executor executor, MainThread mainThread, View view, Repository<com.espweb.chronos.domain.model.Disciplina> disciplinaRepository) {
        super(executor, mainThread);
        this.view = view;
        this.disciplinaRepository = disciplinaRepository;
    }

    @Override
    public void createDisciplina(Disciplina disciplina) {
        com.espweb.chronos.domain.model.Disciplina dDisciplina = PresentationToDomainConverter.convert(disciplina);
        CreateDisciplinaInteractor createDisciplinaInteractor = new CreateDisciplinaInteractorImpl(
                executor,
                mainThread,
                this,
                disciplinaRepository,
                dDisciplina
        );
        createDisciplinaInteractor.execute();
    }

    @Override
    public void updateDisciplina(Disciplina disciplina) {
        com.espweb.chronos.domain.model.Disciplina dDisciplina = PresentationToDomainConverter.convert(disciplina);
        UpdateDisciplinaInteractor updateDisciplinaInteractor = new UpdateDisciplinaInteractorImpl(
                executor,
                mainThread,
                this,
                disciplinaRepository,
                dDisciplina
        );
        updateDisciplinaInteractor.execute();
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
    public void onDisciplinaCreated(com.espweb.chronos.domain.model.Disciplina disciplina) {
        Disciplina pDisciplina = DomainToPresentationConverter.convert(disciplina);
        view.onDisciplinaCreated(pDisciplina);
    }

    @Override
    public void onDisciplinaUpdated() {
        view.onDisciplinaUpdated();
    }

    @Override
    public void onError(String message) {

    }
}
