package com.espweb.chronos.presentation.presenters;

import com.espweb.chronos.domain.model.Assunto;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.model.Disciplina;
import com.espweb.chronos.presentation.presenters.base.BasePresenter;
import com.espweb.chronos.presentation.ui.BaseView;

import java.util.List;

public interface CronogramaPresenter extends BasePresenter {

    interface View extends BaseView {
        void showDisciplinas(List<Disciplina> disciplinas);
        void showCronograma(Cronograma cronograma);
        void addAssuntoToList(Assunto assunto);
        void onDisciplinaDeleted();
        void addDisciplinaToList(Disciplina disciplina);
        void updateDisciplinaOnList(Disciplina disciplina);
    }
    void createAssunto(long disciplinaId, String descricao, String anotacao);
    void getAllDisciplinas(long cronogramaId);
    void getCronograma(long cronogramaId);
    void deleteDisciplina(long disciplinaId);
    void createDisciplina(long cronogramaId, String nome);
    void updateDisciplina(long disciplinaId, String nome);
}
