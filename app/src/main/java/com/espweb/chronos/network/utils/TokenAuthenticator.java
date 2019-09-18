package com.espweb.chronos.network.utils;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.domain.model.Sessao;
import com.espweb.chronos.domain.repository.SessaoRepository;
import com.espweb.chronos.events.SessionHasExpiredEvent;
import com.espweb.chronos.network.RestClient;
import com.espweb.chronos.network.model.TokenResponse;
import com.espweb.chronos.network.model.Token;
import com.espweb.chronos.network.services.SessionService;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import javax.annotation.Nullable;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Route;
import retrofit2.Response;

public class TokenAuthenticator implements Authenticator {
    private SessaoRepository sessaoRepository;

    public TokenAuthenticator(SessaoRepository sessaoRepository) {
        this.sessaoRepository = sessaoRepository;
    }

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, okhttp3.Response response) throws IOException {
        Request originalRequest = response.request();
        Response<TokenResponse> tokenResponse = RestClient.createService(SessionService.class).refreshToken().execute();
        if (tokenResponse.isSuccessful()) {
            Token token = tokenResponse.body().getToken();
            sessaoRepository.refreshToken(token.getValue());
            return originalRequest.newBuilder().addHeader("Authorization", "Bearer " + token.getValue()).build();
        } else {
            sessaoRepository.signOutUser();
            EventBus.getDefault().post(new SessionHasExpiredEvent());
        }
        return null;
    }
}