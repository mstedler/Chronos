package com.espweb.chronos.presentation.presenters.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.cronograma.DeleteCronogramaInteractor;
import com.espweb.chronos.domain.interactors.cronograma.GetCronogramaInteractor;
import com.espweb.chronos.domain.interactors.cronograma.impl.DeleteCronogramaInteractorImpl;
import com.espweb.chronos.domain.interactors.cronograma.impl.GetCronogramaInteractorImpl;
import com.espweb.chronos.domain.interactors.disciplina.DeleteDisciplinaInteractor;
import com.espweb.chronos.domain.interactors.disciplina.GetAllDisciplinasInteractor;
import com.espweb.chronos.domain.interactors.disciplina.impl.DeleteDisciplinaInteractorImpl;
import com.espweb.chronos.domain.interactors.disciplina.impl.GetAllDisciplinasInteractorImpl;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.presentation.converters.DomainToPresentationConverter;
import com.espweb.chronos.presentation.model.Cronograma;
import com.espweb.chronos.presentation.model.Disciplina;
import com.espweb.chronos.presentation.presenters.CronogramaPresenter;
import com.espweb.chronos.presentation.presenters.base.AbstractPresenter;

import java.util.List;

public class CronogramaPresenterImpl extends AbstractPresenter implements CronogramaPresenter,
        GetCronogramaInteractor.Callback,
        GetAllDisciplinasInteractor.Callback,
        DeleteDisciplinaInteractor.Callback, DeleteCronogramaInteractor.Callback {

    private View view;
    private Repository<com.espweb.chronos.domain.model.Disciplina> disciplinaRepository;
    private Repository<com.espweb.chronos.domain.model.Cronograma> cronogramaRepository;

    public CronogramaPresenterImpl(Executor executor,
                                   MainThread mainThread,
                                   View view,
                                   Repository<com.espweb.chronos.domain.model.Cronograma> cronogramaRepository,
                                   Repository<com.espweb.chronos.domain.model.Disciplina> disciplinaRepository) {
        super(executor, mainThread);
        this.view = view;
        this.disciplinaRepository = disciplinaRepository;
        this.cronogramaRepository = cronogramaRepository;
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
    public void onDisciplinasRetrieved(List<com.espweb.chronos.domain.model.Disciplina> disciplinas) {
        List<Disciplina> pDisciplinas = DomainToPresentationConverter.convertDisciplinas(disciplinas);
        view.showDisciplinas(pDisciplinas);
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
    public void onCronogramaRetrieved(com.espweb.chronos.domain.model.Cronograma cronograma) {
        Cronograma pCronograma = DomainToPresentationConverter.convert(cronograma);
        view.setCronograma(pCronograma);
        view.bindCronogramaToView();
    }
}
