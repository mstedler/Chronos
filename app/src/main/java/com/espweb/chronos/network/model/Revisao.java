package com.espweb.chronos.network.model;

import com.google.gson.annotations.SerializedName;

public class Revisao {

    public enum Escopo {
        @SerializedName("0")
        DIARIA,
        @SerializedName("1")
        SEMANAL,
        @SerializedName("2")
        QUINZENAL,
        @SerializedName("3")
        MENSAL;

    }
    @SerializedName("escopo")
    private Escopo escopo;
    @SerializedName("quantidade")
    private int quantidade;

    public Revisao() {
    }

    public Escopo getEscopo() {
        return escopo;
    }

    public void setEscopo(Escopo escopo) {
        this.escopo = escopo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
