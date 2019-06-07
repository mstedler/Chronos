package com.espweb.chronos.domain.interactors.assunto;

import com.espweb.chronos.domain.interactors.base.Interactor;

public interface CreateAssuntoInteractor extends Interactor {
    interface Callback {
        void onAssuntoCreated(long assuntoId);
        void onError(String message);
    }
}
