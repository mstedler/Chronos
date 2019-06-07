package com.espweb.chronos.domain.interactors.revisao;

public interface DeleteRevisaoInteractor {
    interface Callback {
        void onRevisaoDeleted();
        void onError(String message);
    }
}
