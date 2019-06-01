package com.espweb.chronos.network.model;

import com.google.gson.annotations.SerializedName;

public class Exercicio {
    @SerializedName("descricao")
    private String descricao;
    @SerializedName("quantidade")
    private int quantidade;
    @SerializedName("acertos")
    private int acertos;

    public Exercicio() {
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getAcertos() {
        return acertos;
    }

    public void setAcertos(int acertos) {
        this.acertos = acertos;
    }
}
