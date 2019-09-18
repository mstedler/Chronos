package com.espweb.chronos.presentation.presenters;

import com.espweb.chronos.presentation.model.Disciplina;
import com.espweb.chronos.presentation.presenters.base.BasePresenter;
import com.espweb.chronos.presentation.ui.BaseView;

public interface DisciplinaDialogPresenter extends BasePresenter {
    interface View extends BaseView {
        void onDisciplinaCreated(Disciplina disciplina);
        void onDisciplinaUpdated();
    }
    void createDisciplina(Disciplina disciplina);
    void updateDisciplina(Disciplina disciplina);
}
