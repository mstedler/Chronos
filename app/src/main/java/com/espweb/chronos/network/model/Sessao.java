package com.espweb.chronos.network.model;

import com.espweb.chronos.network.converters.RESTModelConverter;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("token")
    String token;

    @SerializedName("user")
    User user;

    public LoginResponse() {

    }

    public User getUser() {
        return user;
    }

    public com.espweb.chronos.domain.model.User getDomainUser() {
        return RESTModelConverter.convertUserToDomainModel(user);
    }

    public String getToken() {
        return token;
    }
}
