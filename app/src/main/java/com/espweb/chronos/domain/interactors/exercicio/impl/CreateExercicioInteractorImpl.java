package com.espweb.chronos.domain.interactors.exercicio.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.exercicio.CreateExercicioInteractor;
import com.espweb.chronos.domain.model.Exercicio;
import com.espweb.chronos.domain.repository.Repository;

public class CreateExercicioInteractorImpl extends AbstractInteractor implements CreateExercicioInteractor {

    private Repository<Exercicio> exercicioRepository;
    private Callback callback;
    private Exercicio exercicio;
    private long assuntoId;

    public CreateExercicioInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                         Callback callback,
                                         Repository<Exercicio> exercicioRepository,
                                         Exercicio exercicio,
                                         long assuntoId) {
        super(threadExecutor, mainThread);

        if (exercicioRepository == null || callback == null) {
            throw new IllegalArgumentException("Argumentos nao podem ser nulos!");
        }

        this.exercicioRepository = exercicioRepository;
        this.callback = callback;
        this.exercicio = exercicio;
        this.assuntoId = assuntoId;
    }

    @Override
    public void run() {
        long exercicioId = exercicioRepository.insert(assuntoId, exercicio);

        if(exercicioId != 0) {
            mainThread.post(() -> callback.onExercicioCreated(exercicioId));
        } else {
            mainThread.post(() -> callback.onError("Erro ao criar exercicio"));
        }
    }
}
