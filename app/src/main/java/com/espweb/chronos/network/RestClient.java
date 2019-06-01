package com.espweb.chronos.network;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    public static final String REST_API_URL = "http://google.com";
    private static Retrofit retrofit;

    static {
        OkHttpClient httpClient = new OkHttpClient.
                Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder requestBuilder = original.newBuilder()
                                .header("User-Agent", "Cronograma-App")
                                .method(original.method(), original.body());

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })
                .readTimeout(5, TimeUnit.MINUTES)
                .build();

        retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(REST_API_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create())).build();
    }

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
