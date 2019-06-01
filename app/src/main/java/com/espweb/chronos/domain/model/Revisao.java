package com.espweb.chronos.domain.model;

public class Revisao {

    public enum Escopo {
        DIARIA,
        SEMANAL,
        QUINZENAL,
        MENSAL;
    }
    private Escopo escopo;
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
