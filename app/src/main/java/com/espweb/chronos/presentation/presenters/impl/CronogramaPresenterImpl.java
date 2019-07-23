package com.espweb.chronos.presentation.presenters.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.assunto.CreateAssuntoInteractor;
import com.espweb.chronos.domain.interactors.assunto.impl.CreateAssuntoInteractorImpl;
import com.espweb.chronos.domain.interactors.cronograma.DeleteCronogramaInteractor;
import com.espweb.chronos.domain.interactors.cronograma.GetCronogramaInteractor;
import com.espweb.chronos.domain.interactors.cronograma.UpdateCronogramaInteractor;
import com.espweb.chronos.domain.interactors.cronograma.impl.DeleteCronogramaInteractorImpl;
import com.espweb.chronos.domain.interactors.cronograma.impl.GetCronogramaInteractorImpl;
import com.espweb.chronos.domain.interactors.cronograma.impl.UpdateCronogramaInteractorImpl;
import com.espweb.chronos.domain.interactors.disciplina.CreateDisciplinaInteractor;
import com.espweb.chronos.domain.interactors.disciplina.DeleteDisciplinaInteractor;
import com.espweb.chronos.domain.interactors.disciplina.GetAllDisciplinasInteractor;
import com.espweb.chronos.domain.interactors.disciplina.UpdateDisciplinaInteractor;
import com.espweb.chronos.domain.interactors.disciplina.impl.CreateDisciplinaInteractorImpl;
import com.espweb.chronos.domain.interactors.disciplina.impl.DeleteDisciplinaInteractorImpl;
import com.espweb.chronos.domain.interactors.disciplina.impl.GetAllDisciplinasInteractorImpl;
import com.espweb.chronos.domain.interactors.disciplina.impl.UpdateDisciplinaInteractorImpl;
import com.espweb.chronos.domain.model.Assunto;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.model.Disciplina;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.presentation.presenters.CronogramaPresenter;
import com.espweb.chronos.presentation.presenters.base.AbstractPresenter;

import java.util.List;

public class CronogramaPresenterImpl extends AbstractPresenter implements CronogramaPresenter,
        GetCronogramaInteractor.Callback,
        GetAllDisciplinasInteractor.Callback,
        DeleteDisciplinaInteractor.Callback, DeleteCronogramaInteractor.Callback {

    private View view;
    private Repository<Disciplina> disciplinaRepository;
    private Repository<Cronograma> cronogramaRepository;
    private Repository<Assunto> assuntoRepository;

    public CronogramaPresenterImpl(Executor executor,
                                   MainThread mainThread,
                                   View view,
                                   Repository<Cronograma> cronogramaRepository,
                                   Repository<Disciplina> disciplinaRepository,
                                   Repository<Assunto> assuntoRepository) {
        super(executor, mainThread);
        this.view = view;
        this.disciplinaRepository = disciplinaRepository;
        this.cronogramaRepository = cronogramaRepository;
        this.assuntoRepository = assuntoRepository;
    }

    @Override
    public void deleteCronograma(long cronogramaId) {
        DeleteCronogramaInteractor deleteCronogramaInteractor = new DeleteCronogramaInteractorImpl(
                executor,
                mainThread,
                this,
                cronogramaRepository,
                cronogramaId
        );
        deleteCronogramaInteractor.execute();
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
    public void deleteDisciplina(long disciplinaId) {
        DeleteDisciplinaInteractor deleteDisciplinaInteractor = new DeleteDisciplinaInteractorImpl(
                executor,
                mainThread,
                this,
                disciplinaRepository,
                disciplinaId);
        deleteDisciplinaInteractor.execute();
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
    public void onDisciplinaDeleted() {
        view.onDisciplinaDeleted();
    }

    @Override
    public void onCronogramaDeleted() {
        view.onCronogramaDeleted();
    }

    @Override
    public void onError(String message) {
        view.showError(message);
    }

    @Override
    public void onCronogramaRetrieved(Cronograma cronograma) {
        view.setCronograma(cronograma);
        view.bindCronogramaToView();
    }
}
