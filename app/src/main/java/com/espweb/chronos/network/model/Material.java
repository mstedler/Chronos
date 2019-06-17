package com.espweb.chronos.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Material {
    @SerializedName("uuid")
    private String uuid;
    @SerializedName("data")
    private Date data;
    @SerializedName("descricao")
    private String descricao;
    @SerializedName("minutos")
    private int minutos;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }
}
