package com.espweb.chronos.presentation.presenters;

import com.espweb.chronos.presentation.model.Cronograma;
import com.espweb.chronos.presentation.presenters.base.BasePresenter;
import com.espweb.chronos.presentation.ui.BaseView;

import java.util.List;

public interface AndamentoGeralPresenter extends BasePresenter {
    interface View extends BaseView {
        void showEmptyView();
        void setCronogramas(List<Cronograma> cronogramas);
        void showCharts();
    }

    public void getAllCronogramas(long userId, boolean fromWeb);
}
