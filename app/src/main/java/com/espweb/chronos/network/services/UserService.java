package com.espweb.chronos.network.services;

import com.espweb.chronos.network.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    @PUT("users/{uuid}")
    @FormUrlEncoded
    Call<User> updateUser(@Field("name") String name, @Path("uuid") String uuid);
}
