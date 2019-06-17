package com.espweb.chronos.presentation.presenters.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.cronograma.GetCronogramaInteractor;
import com.espweb.chronos.domain.interactors.cronograma.impl.GetCronogramaInteractorImpl;
import com.espweb.chronos.domain.interactors.disciplina.GetAllDisciplinasInteractor;
import com.espweb.chronos.domain.interactors.disciplina.impl.GetAllDisciplinasInteractorImpl;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.model.Disciplina;
import com.espweb.chronos.domain.repository.CronogramaRepository;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.presentation.presenters.CronogramaPresenter;
import com.espweb.chronos.presentation.presenters.base.AbstractPresenter;
import com.espweb.chronos.storage.DisciplinaRepositoryImpl;

import java.util.List;

public class CronogramaPresenterImpl extends AbstractPresenter implements CronogramaPresenter,
        GetCronogramaInteractor.Callback,
        GetAllDisciplinasInteractor.Callback {

    private View view;
    private Repository<Disciplina> disciplinaRepository;
    private CronogramaRepository cronogramaRepository;

    public CronogramaPresenterImpl(Executor executor, MainThread mainThread, View view, CronogramaRepository cronogramaRepository, Repository<Disciplina> disciplinaRepository) {
        super(executor, mainThread);
        this.view = view;
        this.disciplinaRepository = disciplinaRepository;
        this.cronogramaRepository = cronogramaRepository;
    }

    @Override
    public void getAllDisciplinas(long cronogramaId) {
        GetAllDisciplinasInteractor getAllDisciplinasInteractor = new GetAllDisciplinasInteractorImpl(
                executor,
                mainThread,
                this,
                disciplinaRepository,
                cronogramaId);
        getAllDisciplinasInteractor.execute();
    }

    @Override
    public void getCronograma(long cronogramaId) {
        GetCronogramaInteractor getCronogramaInteractor = new GetCronogramaInteractorImpl(
                executor,
                mainThread,
                this,
                cronogramaRepository,
                cronogramaId);

        getCronogramaInteractor.execute();
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
    public void onDisciplinasRetrieved(List<Disciplina> disciplinas) {
        view.showDisciplinas(disciplinas);
    }

    @Override
    public void onError(String message) {
        view.showError(message);
    }

    @Override
    public void showCronograma(Cronograma cronograma) {
        view.showCronograma(cronograma);
    }
}
