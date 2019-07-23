package com.espweb.chronos.network.model;

import com.espweb.chronos.network.converters.RESTModelConverter;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Sessao {
    @SerializedName("token")
    String token;

    @SerializedName("user")
    User user;

    public Sessao() {

    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}
