package com.espweb.chronos.presentation.presenters;

import com.espweb.chronos.presentation.model.Cronograma;
import com.espweb.chronos.presentation.model.Disciplina;
import com.espweb.chronos.presentation.presenters.base.BasePresenter;
import com.espweb.chronos.presentation.ui.BaseView;

import java.util.List;

public interface CronogramaPresenter extends BasePresenter {
    interface View extends BaseView {
        void showDisciplinas(List<Disciplina> disciplinas);
        void setCronograma(Cronograma cronograma);
        void bindCronogramaToView();
        void onDisciplinaDeleted();
        void onCronogramaDeleted();
        void navigateToMain();
        void showEmptyView();
    }

    void deleteCronograma(Cronograma cronograma);
    void getAllDisciplinas(long cronogramaId);
    void getCronograma(long cronogramaId);
    void deleteDisciplina(Disciplina disciplina);
}
