package com.espweb.chronos.network.utils;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.domain.model.Sessao;
import com.espweb.chronos.domain.repository.SessaoRepository;
import com.espweb.chronos.network.RestClient;
import com.espweb.chronos.network.model.TokenResponse;
import com.espweb.chronos.network.model.Token;
import com.espweb.chronos.network.services.SessionService;

import java.io.IOException;

import javax.annotation.Nullable;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Route;
import retrofit2.Response;

public class TokenSupport implements Interceptor, Authenticator {
    private SessaoRepository sessaoRepository;

    public TokenSupport(SessaoRepository sessaoRepository) {
        this.sessaoRepository = sessaoRepository;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Sessao sessao;
        try {
            sessao = sessaoRepository.getSessao();
        } catch (NotFoundException e) {
            return chain.proceed(originalRequest);
        }

        Request withTokenRequest = originalRequest.newBuilder().addHeader("Authorization", "Bearer " + sessao.getToken()).build();

        return chain.proceed(withTokenRequest);
    }

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, okhttp3.Response response) throws IOException {
        Request originalRequest = response.request();
        Response<TokenResponse> tokenResponse = RestClient.createService(SessionService.class).refreshToken().execute();
        if (tokenResponse.isSuccessful()) {
            Token token = tokenResponse.body().getToken();
            try {
                sessaoRepository.refreshToken(token.getValue());
            } catch (NotFoundException e) {
                //sessao não encontrada
                return null;
            }


            return originalRequest.newBuilder().addHeader("Authorization", "Bearer " + token.getValue()).build();
        } else {
            return null;
        }
    }
}