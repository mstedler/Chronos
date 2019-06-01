package com.espweb.chronos.network.model;

import com.google.gson.annotations.SerializedName;

public class Material {
    @SerializedName("descricao")
    private String descricao;
    @SerializedName("porcentagem")
    private int porcentagem;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(int porcentagem) {
        this.porcentagem = porcentagem;
    }
}
