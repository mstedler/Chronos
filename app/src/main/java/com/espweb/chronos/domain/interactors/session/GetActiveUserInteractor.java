package com.espweb.chronos.domain.interactors.session;

import com.espweb.chronos.domain.interactors.base.Interactor;
import com.espweb.chronos.domain.model.User;

public interface GetActiveUserInteractor extends Interactor {
    interface Callback {
        void onUserRetrieved(User user);
        void onUserNotFound();
    }
}
