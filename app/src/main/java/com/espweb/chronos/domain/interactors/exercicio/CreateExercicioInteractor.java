package com.espweb.chronos.domain.interactors.exercicio;

import com.espweb.chronos.domain.interactors.base.Interactor;

public interface CreateExercicioInteractor extends Interactor {
    interface Callback {
        void onExercicioCreated(long exercicioId);
        void onError(String message);
    }
}
