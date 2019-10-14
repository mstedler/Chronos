package com.espweb.chronos.presentation.presenters;

import com.espweb.chronos.presentation.model.Cronograma;
import com.espweb.chronos.presentation.presenters.base.BasePresenter;
import com.espweb.chronos.presentation.ui.BaseView;

import java.util.List;

public interface AndamentoPorCronogramaPresenter extends BasePresenter {
    interface View extends BaseView {

        void setCronogramas(List<Cronograma> cronogramas);

        void showCharts();

        void showEmptyView();
    }

    public void getAllCronogramas(long userId, boolean fromWeb);
}
