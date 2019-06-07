package com.espweb.chronos.domain.interactors.assunto;

import com.espweb.chronos.domain.interactors.base.Interactor;
import com.espweb.chronos.domain.model.Assunto;

import java.util.List;

public interface GetAllAssuntosInteractor extends Interactor {
    interface Callback {
        void onAssuntosRetrieved(List<Assunto> assuntos);
        void onError(String message);
    }
}
