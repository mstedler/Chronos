package com.espweb.chronos.domain.interactors.sessao;

import com.espweb.chronos.domain.interactors.base.Interactor;

public interface ResetPasswordInteractor extends Interactor {
    interface Callback {
        void onError(String message);
        void onRequestSent();
    }
}
