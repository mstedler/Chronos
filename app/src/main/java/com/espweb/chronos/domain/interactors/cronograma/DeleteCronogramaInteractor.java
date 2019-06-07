package com.espweb.chronos.domain.interactors.cronograma;

import com.espweb.chronos.domain.interactors.base.Interactor;

public interface DeleteCronogramaInteractor extends Interactor {
    interface Callback {
        void onCronogramaDeleted();
        void onError(String message);
    }
}
