package com.espweb.chronos.presentation.presenters;

import com.espweb.chronos.presentation.model.Artefato;
import com.espweb.chronos.presentation.presenters.base.BasePresenter;
import com.espweb.chronos.presentation.ui.BaseView;

import java.util.List;

public interface AssuntoPresenter extends BasePresenter {
    interface View extends BaseView {
        void showArtefatos(List<Artefato> artefatos);
    }
    void getAllArtefatos(long assuntoId);
}
