package com.espweb.chronos.domain.interactors.material;

import com.espweb.chronos.domain.interactors.base.Interactor;

public interface DeleteMaterialInteractor extends Interactor {
    interface Callback {
        void onMaterialDeleted();
        void onError(String message);
    }
}
