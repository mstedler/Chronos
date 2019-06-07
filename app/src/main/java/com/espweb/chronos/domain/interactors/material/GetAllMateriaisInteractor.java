package com.espweb.chronos.domain.interactors.material;

import com.espweb.chronos.domain.interactors.base.Interactor;
import com.espweb.chronos.domain.model.Material;

import java.util.List;

public interface GetAllMateriaisInteractor extends Interactor {
    interface Callback {
        void onMateriaisRetrieved(List<Material> materiais);
        void onError(String message);
    }
}
