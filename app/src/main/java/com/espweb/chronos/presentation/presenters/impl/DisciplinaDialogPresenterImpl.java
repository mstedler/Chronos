package com.espweb.chronos.presentation.presenters.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.disciplina.CreateDisciplinaInteractor;
import com.espweb.chronos.domain.interactors.disciplina.UpdateDisciplinaInteractor;
import com.espweb.chronos.domain.interactors.disciplina.impl.CreateDisciplinaInteractorImpl;
import com.espweb.chronos.domain.interactors.disciplina.impl.UpdateDisciplinaInteractorImpl;
import com.espweb.chronos.domain.model.Disciplina;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.presentation.presenters.base.AbstractPresenter;
import com.espweb.chronos.presentation.presenters.DisciplinaDialogPresenter;

public class DisciplinaDialogPresenterImpl extends AbstractPresenter implements DisciplinaDialogPresenter, CreateDisciplinaInteractor.Callback, UpdateDisciplinaInteractor.Callback {

    private View view;
    private Repository<Disciplina> disciplinaRepository;

    public DisciplinaDialogPresenterImpl(Executor executor, MainThread mainThread, View view, Repository<Disciplina> disciplinaRepository) {
        super(executor, mainThread);
        this.view = view;
        this.disciplinaRepository = disciplinaRepository;
    }

    @Override
    public void createDisciplina(long cronogramaId, Disciplina disciplina) {
        CreateDisciplinaInteractor createDisciplinaInteractor = new CreateDisciplinaInteractorImpl(
                executor,
                mainThread,
                this,
                disciplinaRepository,
                cronogramaId,
                disciplina
        );
        createDisciplinaInteractor.execute();
    }

    @Override
    public void updateDisciplina(Disciplina disciplina) {
        UpdateDisciplinaInteractor updateDisciplinaInteractor = new UpdateDisciplinaInteractorImpl(
                executor,
                mainThread,
                this,
                disciplinaRepository,
                disciplina
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
    public void onDisciplinaCreated(Disciplina disciplina) {
        view.onDisciplinaCreated(disciplina);
    }

    @Override
    public void onDisciplinaUpdated() {
        view.onDisciplinaUpdated();
    }

    @Override
    public void onError(String message) {

    }
}
