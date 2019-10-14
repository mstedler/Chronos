package com.espweb.chronos.presentation.presenters;

import com.espweb.chronos.presentation.presenters.base.BasePresenter;
import com.espweb.chronos.presentation.ui.BaseView;

public interface ProfilePresenter extends BasePresenter {
    interface View extends BaseView {
        void showLoginView();
        void setCronogramaCount(int count);
    }
    void logout();
    void getCronogramaCount(long userId);
}
