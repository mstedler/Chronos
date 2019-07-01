package com.espweb.chronos.domain.interactors.artefato;

import com.espweb.chronos.domain.interactors.base.Interactor;
import com.espweb.chronos.domain.model.Artefato;

public interface CreateArtefatoInteractor extends Interactor {
    interface Callback{
        void onArtefatoCreated(Artefato artefato);
        void onError(String message);
    }
}
