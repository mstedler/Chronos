package com.espweb.chronos.presentation.presenters;

import com.espweb.chronos.domain.model.Assunto;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.model.Disciplina;
import com.espweb.chronos.presentation.presenters.base.BasePresenter;
import com.espweb.chronos.presentation.ui.BaseView;

import java.util.List;

public interface CronogramaPresenter extends BasePresenter {

    void deleteCronograma(long cronogramaId);

    interface View extends BaseView {
        void showDisciplinas(List<Disciplina> disciplinas);
        void setCronograma(Cronograma cronograma);
        void bindCronogramaToView();
        void onDisciplinaDeleted();
        void onCronogramaDeleted();
    }

    void getAllDisciplinas(long cronogramaId);
    void getCronograma(long cronogramaId);
    void deleteDisciplina(long disciplinaId);
}
