package com.espweb.chronos.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cronogramas {
    @SerializedName("cronogramas")
    List<Cronograma> cronogramas;

    public List<Cronograma> getCronogramas() {
        return cronogramas;
    }
}
