package com.espweb.chronos.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Revisao {

    @SerializedName("uuid")
    private String uuid;
    @SerializedName("data")
    private Date data;

    public enum Escopo {
        @SerializedName("0")
        DIARIA,
        @SerializedName("1")
        SEMANAL,
        @SerializedName("2")
        QUINZENAL,
        @SerializedName("3")
        MENSAL;

        public int getValue(){
            switch (this){
                case DIARIA:
                    return 0;
                case SEMANAL:
                    return 1;
                case QUINZENAL:
                    return 2;
                case MENSAL:
                    return 3;
                default:
                    return 0;
            }
        }

    }
    @SerializedName("escopo")
    private Escopo escopo;

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

    public Revisao() {
    }

    public Escopo getEscopo() {
        return escopo;
    }

    public void setEscopo(Escopo escopo) {
        this.escopo = escopo;
    }
}
