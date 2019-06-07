package com.espweb.chronos.domain.interactors.material;

import com.espweb.chronos.domain.interactors.base.Interactor;

public interface CreateMaterialInteractor extends Interactor {
    interface Callback {
        void onMaterialCreated(long materialId);
        void onError(String message);
    }
}
