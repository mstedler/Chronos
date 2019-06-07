package com.espweb.chronos.domain.interactors.exercicio;

import com.espweb.chronos.domain.interactors.base.Interactor;

public interface DeleteExercicioInteractor extends Interactor {
    interface Callback {
        void onExercicioDeleted();
        void onError(String message);
    }
}
