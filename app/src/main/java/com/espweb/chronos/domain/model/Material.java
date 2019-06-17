package com.espweb.chronos.domain.model;

public class Material extends Artefato{
    private String descricao;
    private int minutos;

    public Material() {
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
