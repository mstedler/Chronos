package com.espweb.chronos.domain.model;

import java.util.Date;

public class Exercicio extends Artefato{
    private String descricao;
    private int quantidade;
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
