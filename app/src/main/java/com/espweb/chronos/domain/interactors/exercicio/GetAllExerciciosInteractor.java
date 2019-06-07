package com.espweb.chronos.domain.interactors.exercicio;

import com.espweb.chronos.domain.interactors.base.Interactor;
import com.espweb.chronos.domain.model.Exercicio;

import java.util.List;

public interface GetAllExerciciosInteractor extends Interactor {
    interface Callback {
        void onExerciciosRetrieved(List<Exercicio> exercicios);
        void onError(String message);
    }
}
