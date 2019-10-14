package com.espweb.chronos.presentation.presenters;

import com.espweb.chronos.presentation.presenters.base.BasePresenter;
import com.espweb.chronos.presentation.ui.BaseView;

public interface ResetPasswordPresenter extends BasePresenter {
    interface View extends BaseView {
        void onInvalidEmail();
        void onRequestSent();
    }
    void resetPassword(String email);
}
