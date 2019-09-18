package com.espweb.chronos.domain.interactors.assunto;

import com.espweb.chronos.domain.interactors.base.Interactor;
import com.espweb.chronos.domain.model.Assunto;

public interface GetAssuntoInteractor extends Interactor {
    interface Callback {
        void onAssuntoRetrieved(Assunto assunto);
        void onAssuntoNotFound();
    }
}
