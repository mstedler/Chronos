package com.espweb.chronos.presentation.presenters;

import com.espweb.chronos.presentation.model.User;
import com.espweb.chronos.presentation.presenters.base.BasePresenter;
import com.espweb.chronos.presentation.ui.BaseView;

public interface ProfileDialogPresenter extends BasePresenter {
    interface View extends BaseView {
        void onUserUpdated();
    }

    void updateUser(User user);
}
