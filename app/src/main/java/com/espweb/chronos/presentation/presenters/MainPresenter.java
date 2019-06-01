package com.espweb.chronos.presentation.presenters;

import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.presentation.presenters.base.BasePresenter;
import com.espweb.chronos.presentation.ui.BaseView;

import java.util.List;


public interface MainPresenter extends BasePresenter {

    interface View extends BaseView {
        void showCronogramas(List<Cronograma> cronogramas);
    }
    void getAllCronogramas();
}
