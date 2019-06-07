package com.espweb.chronos.domain.interactors.disciplina.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.disciplina.UpdateDisciplinaInteractor;
import com.espweb.chronos.domain.model.Disciplina;
import com.espweb.chronos.domain.repository.Repository;

public class UpdateDisciplinaInteractorImpl extends AbstractInteractor implements UpdateDisciplinaInteractor {

    private Repository<Disciplina> disciplinaRepository;
    private Callback callback;
    private Disciplina disciplina;


    public UpdateDisciplinaInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                          Callback callback,
                                          Repository<Disciplina> disciplinaRepository,
                                          Disciplina disciplina) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.disciplinaRepository = disciplinaRepository;
        this.disciplina = disciplina;
    }

    @Override
    public void run() {
        boolean success = disciplinaRepository.update(disciplina);

        if(success) {
            mainThread.post(() -> callback.onDisciplinaUpdated());
        } else {
            mainThread.post(() -> callback.onError("Erro ao atualizar disciplina"));
        }
    }
}
