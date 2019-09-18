package com.espweb.chronos.domain.interactors.sessao;

import com.espweb.chronos.domain.interactors.base.Interactor;
import com.espweb.chronos.domain.model.Sessao;

public interface GetSessaoInteractor extends Interactor {
    interface Callback {
        void onSessaoRetrieved(Sessao sessao);
        void onSessaoNotFound();
    }
}
