package com.espweb.chronos.network.model;

import com.google.gson.annotations.SerializedName;

public class Error {
    @SerializedName("error")
    String message;

    public String getMessage() {
        return message;
    }
}
