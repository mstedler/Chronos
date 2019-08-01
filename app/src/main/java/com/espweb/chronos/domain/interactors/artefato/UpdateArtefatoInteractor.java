package com.espweb.chronos.domain.interactors.artefato;

import com.espweb.chronos.domain.interactors.base.Interactor;

public interface UpdateArtefatoInteractor extends Interactor {

    interface Callback {
        void onArtefatoUpdated();
    }

}
