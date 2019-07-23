package com.espweb.chronos.network.model;

import com.google.gson.annotations.SerializedName;

public class TokenResponse {
    @SerializedName("data")
    private Token token;

    public TokenResponse() {
    }

    public Token getToken() {
        return token;
    }
}
