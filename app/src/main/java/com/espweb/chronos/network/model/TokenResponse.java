package com.espweb.chronos.network.model;

import com.google.gson.annotations.SerializedName;

public class RefreshTokenResponse {
    @SerializedName("data")
    private Token token;

    public RefreshTokenResponse() {
    }

    public Token getToken() {
        return token;
    }
}
