package com.espweb.chronos.presentation.presenters;

import com.espweb.chronos.presentation.presenters.base.BasePresenter;
import com.espweb.chronos.presentation.ui.BaseView;

public interface SignUpPresenter extends BasePresenter {

    interface View extends BaseView {
        void signUpSuccess();
        void signUpFailed(String message);
        void showNameError();
        void showEmailError();
        void showPasswordError();
        void clearErrors();
    }

    void signUp(String name, String email, String password);

}