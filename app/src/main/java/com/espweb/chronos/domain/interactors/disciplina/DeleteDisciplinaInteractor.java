package com.espweb.chronos.domain.interactors.disciplina;

import com.espweb.chronos.domain.interactors.base.Interactor;

public interface DeleteDisciplinaInteractor extends Interactor {
    interface Callback {
        void onDisciplinaDeleted();
        void onError(String message);
    }
}
