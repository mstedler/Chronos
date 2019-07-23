package com.espweb.chronos.domain.interactors.cronograma;

import com.espweb.chronos.domain.interactors.base.Interactor;
import com.espweb.chronos.domain.model.Cronograma;

public interface CreateCronogramaInteractor extends Interactor {
    interface Callback {
        void onCronogramaCreated(Cronograma cronograma);
        void onError(String message);
    }
}
