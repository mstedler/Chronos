package com.espweb.chronos.domain.interactors.exercicio;

import com.espweb.chronos.domain.interactors.base.Interactor;

public interface UpdateExercicioInteractor extends Interactor {
    interface Callback {
        void onExercicioUpdated();
        void onError(String message);
    }
}
