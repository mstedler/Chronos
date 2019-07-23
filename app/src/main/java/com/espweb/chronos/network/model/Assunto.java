package com.espweb.chronos.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Assunto {
    @SerializedName("uuid")
    private String uuid;
    @SerializedName("descricao")
    private String descricao;
    @SerializedName("materiais")
    private List<Material> materiais;
    @SerializedName("revisoes")
    private List<Revisao> revisoes;
    @SerializedName("exercicios")
    private List<Exercicio> exercicios;

    public Assunto() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Material> getMateriais() {
        return materiais;
    }

    public void setMateriais(List<Material> materiais) {
        this.materiais = materiais;
    }

    public List<Revisao> getRevisoes() {
        return revisoes;
    }

    public void setRevisoes(List<Revisao> revisoes) {
        this.revisoes = revisoes;
    }

    public List<Exercicio> getExercicios() {
        return exercicios;
    }

    public void setExercicios(List<Exercicio> exercicios) {
        this.exercicios = exercicios;
    }
}
