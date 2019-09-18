package com.espweb.chronos.presentation.presenters;

import com.espweb.chronos.presentation.model.Artefato;
import com.espweb.chronos.presentation.model.Assunto;
import com.espweb.chronos.presentation.presenters.base.BasePresenter;
import com.espweb.chronos.presentation.ui.BaseView;

import java.util.List;

public interface AssuntoPresenter extends BasePresenter {
    void deleteAssunto(Assunto assunto);
    void deleteArtefato(Artefato artefato);

    interface View extends BaseView {
        void showArtefatos(List<Artefato> artefatos);
        void navigateToCronograma();
        void setAssunto(Assunto pAssunto);
        void showEmptyView();
        void onArtefatoDeleted();
        void onArtefatoNotFound();
    }
    void getAssunto(long id);
    void getAllArtefatos(long assuntoId);
}
