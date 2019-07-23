package com.espweb.chronos.presentation.presenters;

import com.espweb.chronos.domain.model.Disciplina;
import com.espweb.chronos.presentation.presenters.base.BasePresenter;
import com.espweb.chronos.presentation.ui.BaseView;

public interface DisciplinaDialogPresenter extends BasePresenter {
    interface View extends BaseView {
        void setNomeError(String message);
        void onDisciplinaCreated(Disciplina disciplina);
        void onDisciplinaUpdated();
    }
    void createDisciplina(long cronogramaId, Disciplina disciplina);
    void updateDisciplina(Disciplina disciplina);
}
