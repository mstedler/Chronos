package com.espweb.chronos.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Exercicio {
    @SerializedName("uuid")
    private String uuid;
    @SerializedName("data")
    private Date data;
    @SerializedName("descricao")
    private String descricao;
    @SerializedName("quantidade")
    private int quantidade;
    @SerializedName("acertos")
    private int acertos;

    @SerializedName("escopo")
    private int escopo = 0;

    @SerializedName("assunto_uuid")
    private String assuntoUuid;

    public String getAssuntoUuid() {
        return assuntoUuid;
    }

    public void setAssuntoUuid(String assuntoUuid) {
        this.assuntoUuid = assuntoUuid;
    }

    public Exercicio() {
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

    public int getEscopo() {
        return escopo;
    }

    public void setEscopo(int escopo) {
        this.escopo = escopo;
    }
}
