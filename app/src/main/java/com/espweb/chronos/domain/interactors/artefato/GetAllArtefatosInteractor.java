package com.espweb.chronos.domain.interactors.artefato;

import com.espweb.chronos.domain.interactors.base.Interactor;
import com.espweb.chronos.domain.model.Artefato;

import java.util.List;

public interface GetAllArtefatosInteractor extends Interactor {
    interface Callback {
        void onArtefatosRetrieved(List<Artefato> artefatos);
        void onError(String message);
    }
}
