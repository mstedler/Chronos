package com.espweb.chronos.domain.interactors.disciplina;

import com.espweb.chronos.domain.interactors.base.Interactor;
import com.espweb.chronos.domain.model.Disciplina;

import java.util.List;

public interface GetAllDisciplinasInteractor extends Interactor {
    interface Callback {
        void onDisciplinasRetrieved(List<Disciplina> disciplinas);
        void onError(String message);
        void onDisciplinasNotFound();
    }
}
