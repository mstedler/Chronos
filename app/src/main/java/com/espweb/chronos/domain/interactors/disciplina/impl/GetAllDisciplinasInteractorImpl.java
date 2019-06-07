package com.espweb.chronos.domain.interactors.disciplina.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.disciplina.GetAllDisciplinasInteractor;
import com.espweb.chronos.domain.model.Disciplina;
import com.espweb.chronos.domain.repository.Repository;

import java.util.List;

public class GetAllDisciplinasInteractorImpl extends AbstractInteractor implements GetAllDisciplinasInteractor {

    private Repository<Disciplina> disciplinaRepository;
    private Callback callback;
    private long cronogramaId;

    public GetAllDisciplinasInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                           Callback callback,
                                           Repository<Disciplina> disciplinaRepository,
                                           long cronogramaId) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.disciplinaRepository = disciplinaRepository;
        this.cronogramaId = cronogramaId;
    }

    @Override
    public void run() {
        List<Disciplina> disciplinas = disciplinaRepository.getAll(cronogramaId);

        if(disciplinas.size() > 0) {
            mainThread.post(() -> callback.onDisciplinasRetrieved(disciplinas));
        } else {
            mainThread.post(() -> callback.onError("Erro ao listar disciplinas"));
        }
    }
}
