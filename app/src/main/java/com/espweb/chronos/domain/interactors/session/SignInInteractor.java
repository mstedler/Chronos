package com.espweb.chronos.domain.interactors.session;

import com.espweb.chronos.domain.interactors.base.Interactor;
import com.espweb.chronos.domain.model.Sessao;
import com.espweb.chronos.domain.model.User;

public interface SignInInteractor extends Interactor {
    interface Callback {
        void onSignInSuccess(User user);
        void onError(String message);
    }
}
