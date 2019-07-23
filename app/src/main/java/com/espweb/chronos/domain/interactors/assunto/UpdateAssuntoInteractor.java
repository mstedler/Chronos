package com.espweb.chronos.domain.interactors.assunto;

import com.espweb.chronos.domain.interactors.base.Interactor;
import com.espweb.chronos.domain.model.Assunto;

public interface UpdateAssuntoInteractor extends Interactor {
    interface Callback {
        void onAssuntoUpdated();
        void onError(String message);
    }
}
