package com.espweb.chronos.domain.interactors.revisao;

import com.espweb.chronos.domain.model.Revisao;

import java.util.List;

public interface GetAllRevisoesInteractor {
    interface Callback {
        void onRevisoesRetrieved(List<Revisao> revisoes);
        void onError(String message);
    }
}
