package com.espweb.chronos.domain.interactors.cronograma;

import com.espweb.chronos.domain.interactors.base.Interactor;

public interface CreateCronogramaInteractor extends Interactor {
    interface Callback {
        void onCronogramaCreated(long cronogramaId);
        void onError(String message);
    }
}
