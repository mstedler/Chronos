package com.espweb.chronos.domain.interactors.disciplina.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.disciplina.CreateDisciplinaInteractor;
import com.espweb.chronos.domain.model.Disciplina;
import com.espweb.chronos.domain.repository.Repository;

public class CreateDisciplinaInteractorImpl extends AbstractInteractor implements CreateDisciplinaInteractor {

    private Repository<Disciplina> disciplinaRepository;
    private Callback callback;
    private Disciplina disciplina;

    public CreateDisciplinaInteractorImpl(Executor threadExecutor, MainThread mainThread,
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
        try {
            long disciplinaId = disciplinaRepository.insert(disciplina);
            disciplina.setId(disciplinaId);
            mainThread.post(() -> callback.onDisciplinaCreated(disciplina));
        } catch (Exception e) {
            mainThread.post(() -> callback.onError("Erro ao criar disciplina."));
        }
    }
}
