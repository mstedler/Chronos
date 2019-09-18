package com.espweb.chronos.domain.model;

public class Exercicio extends Artefato{
    private int quantidade;
    private int acertos;

    public Exercicio() {

    }

    @Override
    public EnumTipo getTipo() {
        return EnumTipo.EXERCICIO;
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
