package com.espweb.chronos.domain.interactors.exercicio.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.exercicio.GetAllExerciciosInteractor;
import com.espweb.chronos.domain.model.Exercicio;
import com.espweb.chronos.domain.repository.Repository;

import java.util.List;

public class GetAllExerciciosInteractorImpl extends AbstractInteractor implements GetAllExerciciosInteractor {

    private Callback callback;
    private Repository<Exercicio> exercicioRepository;
    private long assuntoId;


    public GetAllExerciciosInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                          Callback callback,
                                          Repository<Exercicio> exercicioRepository,
                                          long assuntoId) {
        super(threadExecutor, mainThread);

        if (exercicioRepository == null || callback == null) {
            throw new IllegalArgumentException("Argumentos nao podem ser nulos!");
        }

        this.exercicioRepository = exercicioRepository;
        this.callback = callback;
        this.assuntoId = assuntoId;
    }

    @Override
    public void run() {
        List<Exercicio> exercicios = exercicioRepository.getAll(assuntoId);

        if(exercicios.size() > 0) {
            mainThread.post(() -> callback.onExerciciosRetrieved(exercicios));
        } else {
            mainThread.post(() -> callback.onError("Erro ao listar exercicios"));
        }
    }
}
