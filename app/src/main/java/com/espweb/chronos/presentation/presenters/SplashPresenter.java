package com.espweb.chronos.presentation.presenters;

import com.espweb.chronos.presentation.presenters.base.BasePresenter;
import com.espweb.chronos.presentation.ui.BaseView;

public interface SplashPresenter extends BasePresenter {
    interface View extends BaseView {
        void navigateToMain();
        void navigateToLogin();
    }
    void checkSessao();
}
