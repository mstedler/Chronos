package com.espweb.chronos.domain.interactors.user;

import com.espweb.chronos.domain.interactors.base.Interactor;

public interface UpdateUserInteractor extends Interactor {
    interface Callback {
        void onUserUpdated();
        void onError(String message);
    }
}
