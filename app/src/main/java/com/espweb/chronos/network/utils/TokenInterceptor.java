package com.espweb.chronos.network.utils;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.domain.model.Sessao;
import com.espweb.chronos.domain.repository.SessaoRepository;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    private SessaoRepository sessaoRepository;

    public TokenInterceptor(SessaoRepository sessaoRepository) {
        this.sessaoRepository = sessaoRepository;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        try {
            Sessao sessao = sessaoRepository.getSessao();
            Request withTokenRequest = originalRequest.newBuilder().addHeader("Authorization", "Bearer " + sessao.getToken()).build();
            return chain.proceed(withTokenRequest);
        } catch (NotFoundException e) {
            return chain.proceed(originalRequest);
        }
    }
}
