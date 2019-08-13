package com.espweb.chronos.presentation.presenters;

import com.espweb.chronos.presentation.presenters.base.BasePresenter;
import com.espweb.chronos.presentation.ui.BaseView;

public interface LoginPresenter extends BasePresenter {
    interface View extends BaseView {
        void showSignInView();
        void showSignUpView();
    }
    void showSignIn();
    void showSignUp();
}