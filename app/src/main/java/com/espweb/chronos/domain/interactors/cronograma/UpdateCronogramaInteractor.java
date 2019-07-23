package com.espweb.chronos.domain.interactors.cronograma;

import com.espweb.chronos.domain.interactors.base.Interactor;
import com.espweb.chronos.domain.model.Cronograma;

public interface UpdateCronogramaInteractor extends Interactor {
    interface Callback {
        void onCronogramaUpdated();
        void onError(String message);
    }
}
