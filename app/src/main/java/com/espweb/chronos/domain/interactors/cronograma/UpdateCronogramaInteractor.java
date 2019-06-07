package com.espweb.chronos.domain.interactors.cronograma;

import com.espweb.chronos.domain.interactors.base.Interactor;

public interface UpdateCronogramaInteractor extends Interactor {
    interface Callback {
        void onCronogramaUpdated();
        void onError(String message);
    }
}
