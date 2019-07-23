package com.espweb.chronos.network.model;

import com.google.gson.annotations.SerializedName;

public class Token {
    @SerializedName("token")
    private String value;


    public Token() {
    }

    public String getValue() {
        return value;
    }
}
