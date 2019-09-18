package com.espweb.chronos.domain.interactors.artefato;

import com.espweb.chronos.domain.interactors.base.Interactor;

public interface DeleteArtefatoInteractor extends Interactor {
    interface Callback {
        void onArtefatoDeleted();
        void onArtefatoNotValid();
    }
}
