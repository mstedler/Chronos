package com.espweb.chronos.domain.interactors.exercicio.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.exercicio.DeleteExercicioInteractor;
import com.espweb.chronos.domain.model.Exercicio;
import com.espweb.chronos.domain.repository.Repository;

public class DeleteExercicioInteractorImpl extends AbstractInteractor implements DeleteExercicioInteractor {

    private Repository<Exercicio> exercicioRepository;
    private Callback callback;
    private long exercicioId;

    public DeleteExercicioInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                         Callback callback,
                                         Repository<Exercicio> exercicioRepository,
                                         long exercicioId) {
        super(threadExecutor, mainThread);

        if (exercicioRepository == null || callback == null) {
            throw new IllegalArgumentException("Argumentos nao podem ser nulos!");
        }

        this.exercicioRepository = exercicioRepository;
        this.callback = callback;
        this.exercicioId = exercicioId;
    }

    @Override
    public void run() {
        boolean success = exercicioRepository.delete(exercicioId);

        if(success) {
            mainThread.post(() -> callback.onExercicioDeleted());
        } else {
            mainThread.post(() -> callback.onError("Erro ao excluir exercicio."));
        }
    }
}
