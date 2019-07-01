package com.espweb.chronos.domain.interactors.disciplina;

import com.espweb.chronos.domain.interactors.base.Interactor;
import com.espweb.chronos.domain.model.Disciplina;

public interface CreateDisciplinaInteractor extends Interactor {
    interface Callback {
        void onDisciplinaCreated(Disciplina disciplina);
        void onError(String message);
    }
}
