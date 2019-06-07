package com.espweb.chronos.domain.interactors.disciplina;

import com.espweb.chronos.domain.interactors.base.Interactor;

public interface UpdateDisciplinaInteractor extends Interactor {
    interface Callback {
        void onDisciplinaUpdated();
        void onError(String message);
    }
}
