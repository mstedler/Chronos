package com.espweb.chronos.domain.interactors.material;

import com.espweb.chronos.domain.interactors.base.Interactor;

public interface UpdateMaterialInteractor extends Interactor {
    interface Callback {
        void onMaterialUpdated();
        void onError(String message);
    }
}
