package com.espweb.chronos.presentation.presenters;

import com.espweb.chronos.domain.model.User;
import com.espweb.chronos.presentation.presenters.base.BasePresenter;
import com.espweb.chronos.presentation.ui.BaseView;

public interface SignInPresenter extends BasePresenter {
    interface View extends BaseView {
        void setEmailError();
        void setPasswordError();
        void clearErrors();
        void showWelcomeMessage(User user);
        void showMain();
        void blockClick();
        void allowClick();
    }
    void signInUser(String email, String password);
}