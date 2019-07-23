package com.espweb.chronos.network;

import com.espweb.chronos.domain.repository.SessaoRepository;
import com.espweb.chronos.network.utils.ResponseInterceptor;
import com.espweb.chronos.network.utils.TokenAuthenticator;
import com.espweb.chronos.network.utils.TokenInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    public static final String REST_API_URL = "http://cronos.vizzarconsultoria.com/api/";
    private static Retrofit retrofit;

    public static void init(SessaoRepository sessaoRepository) {
        TokenInterceptor tokenInterceptor = new TokenInterceptor(sessaoRepository);
        TokenAuthenticator tokenAuthenticator = new TokenAuthenticator(sessaoRepository);
        ResponseInterceptor responseInterceptor = new ResponseInterceptor(sessaoRepository);
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(1);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(tokenInterceptor)
                .authenticator(tokenAuthenticator)
                .addInterceptor(responseInterceptor)
                .dispatcher(dispatcher)
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setLenient()
                .serializeNulls()
                .create();

        retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(REST_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();

    }

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
