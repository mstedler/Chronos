package com.espweb.chronos.domain.interactors.sessao;

import com.espweb.chronos.domain.interactors.base.Interactor;

public interface SignUpInteractor extends Interactor {
    interface Callback {
        void onSignUpSuccess();
        void onError(String message);
    }
}
