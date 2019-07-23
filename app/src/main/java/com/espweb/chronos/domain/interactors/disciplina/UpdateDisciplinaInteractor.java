package com.espweb.chronos.domain.interactors.disciplina;

import com.espweb.chronos.domain.interactors.base.Interactor;
import com.espweb.chronos.domain.model.Disciplina;

public interface UpdateDisciplinaInteractor extends Interactor {
    interface Callback {
        void onDisciplinaUpdated();
        void onError(String message);
    }
}
