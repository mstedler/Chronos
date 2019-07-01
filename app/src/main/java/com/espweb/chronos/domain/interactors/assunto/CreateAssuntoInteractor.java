package com.espweb.chronos.domain.interactors.assunto;

import com.espweb.chronos.domain.interactors.base.Interactor;
import com.espweb.chronos.domain.model.Assunto;

public interface CreateAssuntoInteractor extends Interactor {
    interface Callback {
        void onAssuntoCreated(Assunto assunto);
        void onError(String message);
    }
}
