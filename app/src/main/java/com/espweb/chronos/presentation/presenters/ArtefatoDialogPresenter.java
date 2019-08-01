package com.espweb.chronos.presentation.presenters;

import com.espweb.chronos.presentation.model.Artefato;
import com.espweb.chronos.presentation.presenters.base.BasePresenter;
import com.espweb.chronos.presentation.ui.BaseView;

public interface ArtefatoDialogPresenter extends BasePresenter {
    interface View extends BaseView {
        void onArtefatoCreated(Artefato artefato);
        void onArtefatoUpdated();
    }

    void createArtefato(long assuntoId, Artefato artefato);
    void updateArtefato(Artefato artefato);
}
