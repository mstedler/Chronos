package com.espweb.chronos.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Revisao {

    @SerializedName("uuid")
    private String uuid;
    @SerializedName("data")
    private Date data;
    @SerializedName("descricao")
    private String descricao;

    public enum Escopo {
        @SerializedName("0")
        DIARIA,
        @SerializedName("1")
        SEMANAL,
        @SerializedName("2")
        QUINZENAL,
        @SerializedName("3")
        MENSAL;

        public static Escopo fromInt(int escopo) {
            switch (escopo) {
                case 0: return DIARIA;
                case 1: return SEMANAL;
                case 2: return QUINZENAL;
                case 3: return MENSAL;
            }
            return DIARIA;
        }

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

    @SerializedName("assunto_uuid")
    private String assuntoUuid;

    public String getAssuntoUuid() {
        return assuntoUuid;
    }

    public void setAssuntoUuid(String assuntoUuid) {
        this.assuntoUuid = assuntoUuid;
    }

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
