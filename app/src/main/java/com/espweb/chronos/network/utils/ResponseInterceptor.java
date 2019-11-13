package com.espweb.chronos.network.utils;

import android.util.Log;

import com.espweb.chronos.domain.repository.SessaoRepository;
import com.espweb.chronos.events.SessionHasExpiredEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ResponseInterceptor implements Interceptor {


    private SessaoRepository sessaoRepository;

    public ResponseInterceptor(SessaoRepository sessaoRepository) {
        this.sessaoRepository = sessaoRepository;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Response response = chain.proceed(originalRequest);

        if (response.isSuccessful()) {
            String token = response.header("Authorization");
            if (token != null) {
                token = token.replace("Bearer ", "");
                sessaoRepository.refreshToken(token);
            }
        } else if(response.code() == 500){
            sessaoRepository.signOutUser();
            EventBus.getDefault().post(new SessionHasExpiredEvent());
        }

        return response;
    }
}
