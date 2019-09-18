package com.espweb.chronos.presentation.presenters;

import com.espweb.chronos.presentation.model.Cronograma;
import com.espweb.chronos.presentation.presenters.base.BasePresenter;
import com.espweb.chronos.presentation.ui.BaseView;

public interface CronogramaDialogPresenter extends BasePresenter {
    interface View extends BaseView {
        void dismissDialog();
        void setTitleError(String errorMessage);
        void setInitialDateError(String errorMessage);
        void setFinalDateError(String errorMessage);
        void cronogramaCreated(Cronograma cronograma);
        void cronogramaUpdated();
    }
    void createCronograma(Cronograma cronograma);
    void updateCronograma(Cronograma cronograma);
}
