package com.espweb.chronos.domain.interactors.disciplina;

import com.espweb.chronos.domain.interactors.base.Interactor;

public interface CreateDisciplinaInteractor extends Interactor {
    interface Callback {
        void onDisciplinaCreated(long id);
        void onError(String message);
    }
}
