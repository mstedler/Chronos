package com.espweb.chronos.domain.interactors.disciplina.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.disciplina.DeleteDisciplinaInteractor;
import com.espweb.chronos.domain.model.Disciplina;
import com.espweb.chronos.domain.repository.Repository;

public class DeleteDisciplinaInteractorImpl extends AbstractInteractor implements DeleteDisciplinaInteractor {

    private Repository<Disciplina> disciplinaRepository;
    private Callback callback;
    private Disciplina disciplina;

    public DeleteDisciplinaInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                          Callback callback,
                                          Repository<Disciplina> disciplinaRepository,
                                          Disciplina disciplina) {
        super(threadExecutor, mainThread);
        if (disciplinaRepository == null || callback == null) {
            throw new IllegalArgumentException("Argumentos nao podem ser nulos!");
        }

        this.callback = callback;
        this.disciplinaRepository = disciplinaRepository;
        this.disciplina = disciplina;
    }

    @Override
    public void run() {
        disciplinaRepository.delete(disciplina.getId());
        mainThread.post(() -> callback.onDisciplinaDeleted());
    }
}
