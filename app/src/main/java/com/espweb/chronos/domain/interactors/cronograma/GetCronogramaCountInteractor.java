package com.espweb.chronos.domain.interactors.cronograma;

import com.espweb.chronos.domain.interactors.base.Interactor;

public interface GetCronogramaCountInteractor extends Interactor {
    interface Callback {
        void onCounted(int count);
        void onError(String message);
    }
}
