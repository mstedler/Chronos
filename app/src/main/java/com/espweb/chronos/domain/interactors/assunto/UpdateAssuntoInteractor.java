package com.espweb.chronos.domain.interactors.assunto;

import com.espweb.chronos.domain.interactors.base.Interactor;

public interface UpdateAssuntoInteractor extends Interactor {
    interface Callback {
        void onAssuntoUpdated();
        void onError(String message);
    }
}
