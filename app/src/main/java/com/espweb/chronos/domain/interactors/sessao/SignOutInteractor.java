package com.espweb.chronos.domain.interactors.sessao;

import com.espweb.chronos.domain.interactors.base.Interactor;

public interface SignOutInteractor extends Interactor {
    interface Callback {
        void onUserSignOut();
        void onError(String message);
    }
}
