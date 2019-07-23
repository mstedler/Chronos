package com.espweb.chronos.domain.interactors.cronograma;


import com.espweb.chronos.domain.interactors.base.Interactor;
import com.espweb.chronos.domain.model.Cronograma;

import java.util.List;


public interface GetAllCronogramasInteractor extends Interactor {

    interface Callback {
        void onCronogramasRetrieved(List<Cronograma> cronogramas);
        void onCronogramasNotFound();
        void onError(String message);
    }
}
