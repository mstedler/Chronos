package com.espweb.chronos.domain.interactors.exercicio.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.exercicio.UpdateExercicioInteractor;
import com.espweb.chronos.domain.model.Exercicio;
import com.espweb.chronos.domain.repository.Repository;

public class UpdateExercicioInteractorImpl extends AbstractInteractor implements UpdateExercicioInteractor {

    private Callback callback;
    private Repository<Exercicio> exercicioRepository;
    private Exercicio exercicio;

    public UpdateExercicioInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                         Callback callback,
                                         Repository<Exercicio> exercicioRepository,
                                         Exercicio exercicio) {
        super(threadExecutor, mainThread);

        if (exercicioRepository == null || callback == null) {
            throw new IllegalArgumentException("Argumentos nao podem ser nulos!");
        }

        this.exercicioRepository = exercicioRepository;
        this.callback = callback;
        this.exercicio = exercicio;
    }

    @Override
    public void run() {
        boolean success = exercicioRepository.update(exercicio);

        if(success) {
            mainThread.post(() -> callback.onExercicioUpdated());
        } else {
            mainThread.post(() -> callback.onError("Erro ao atualizar exercicio"));
        }
    }
}
