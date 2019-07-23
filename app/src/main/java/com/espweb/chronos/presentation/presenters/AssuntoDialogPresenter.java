package com.espweb.chronos.presentation.presenters;

import com.espweb.chronos.domain.model.Assunto;
import com.espweb.chronos.presentation.presenters.base.BasePresenter;
import com.espweb.chronos.presentation.ui.BaseView;

public interface AssuntoDialogPresenter extends BasePresenter {

    interface View extends BaseView {
        void onAssuntoCreated(Assunto assunto);
        void onAssuntoUpdated();
    }

    void createAssunto(long disciplinaId, Assunto assunto);
    void updateAssunto(Assunto assunto);
}
