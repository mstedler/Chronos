package com.espweb.chronos.domain.interactors.assunto;

import com.espweb.chronos.domain.interactors.base.Interactor;

public interface DeleteAssuntoInteractor extends Interactor {
    interface Callback {
        void onAssuntoDeleted();
        void onError(String message);
    }
}
