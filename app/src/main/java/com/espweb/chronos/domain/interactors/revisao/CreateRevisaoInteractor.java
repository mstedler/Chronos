package com.espweb.chronos.domain.interactors.revisao;

public interface CreateRevisaoInteractor {
    interface Callback {
        void onRevisaoCreated(long id);
        void onError(String message);
    }
}
