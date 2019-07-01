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
    private long cronogramaId;
    private String nome;

    public CreateDisciplinaInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                          Callback callback,
                                          Repository<Disciplina> disciplinaRepository,
                                          long cronogramaId,
                                          String nome) {
        super(threadExecutor, mainThread);
        if (disciplinaRepository == null || callback == null) {
            throw new IllegalArgumentException("Argumentos nao podem ser nulos!");
        }

        this.callback = callback;
        this.disciplinaRepository = disciplinaRepository;
        this.cronogramaId = cronogramaId;
        this.nome = nome;
    }

    @Override
    public void run() {
        Disciplina disciplina = new Disciplina();
        disciplina.setNome(nome);

        long disciplinaId = disciplinaRepository.insert(cronogramaId, disciplina);

        if(disciplinaId != 0) {
            disciplina.setId(disciplinaId);
            mainThread.post(() -> callback.onDisciplinaCreated(disciplina));
        } else {
            mainThread.post(() -> callback.onError("Erro ao criar disciplina."));
        }
    }
}
