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
    @SerializedName("time")
    private int time;

    @SerializedName("assunto_uuid")
    private String assuntoUuid;

    public String getAssuntoUuid() {
        return assuntoUuid;
    }

    public void setAssuntoUuid(String assuntoUuid) {
        this.assuntoUuid = assuntoUuid;
    }

    public enum Escopo {
        @SerializedName("1")
        VIDEO_AULA,
        @SerializedName("2")
        LIVROS,
        @SerializedName("3")
        DIVERSOS;

        public int getValue() {
            switch (this){
                case VIDEO_AULA:
                    return 1;
                case LIVROS:
                    return 2;
                case DIVERSOS:
                    return 3;
                default:
                    return 1;
            }
        }

        public static Escopo fromInt(int value) {
            switch (value) {
                case 1: return VIDEO_AULA;
                case 2: return LIVROS;
                case 3: return DIVERSOS;
            }
            return VIDEO_AULA;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Escopo getEscopo() {
        return escopo;
    }

    public void setEscopo(Escopo escopo) {
        this.escopo = escopo;
    }
}
