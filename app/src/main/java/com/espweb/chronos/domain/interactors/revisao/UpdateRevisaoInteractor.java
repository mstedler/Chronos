package com.espweb.chronos.domain.interactors.revisao;

public interface UpdateRevisaoInteractor {
    interface Callback {
        void onRevisaoUpdated();
        void onError(String message);
    }
}
