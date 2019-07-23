package com.espweb.chronos.network.services;

import com.espweb.chronos.network.model.Sessao;
import com.espweb.chronos.network.model.TokenResponse;
import com.espweb.chronos.network.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SessionService {
    @POST("refresh")
    Call<TokenResponse> refreshToken();

    @FormUrlEncoded
    @POST("login")
    Call<Sessao> signIn(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("user")
    Call<User> signUp(@Field("name") String name, @Field("email") String email, @Field("password") String password);
}
