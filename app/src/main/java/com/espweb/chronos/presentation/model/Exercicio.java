package com.espweb.chronos.presentation.model;

import com.espweb.chronos.domain.model.EnumTipo;

import org.parceler.Parcel;

@Parcel(Parcel.Serialization.BEAN)
public class Exercicio extends Artefato {
    private int quantidade;
    private int acertos;

    public Exercicio() {

    }

    public Exercicio(long idAssunto) {
        this.idAssunto = idAssunto;
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

    @Override
    public EnumTipo getTipo() {
        return EnumTipo.EXERCICIO;
    }

    public boolean isValid() {
        return acertos <= quantidade;
    }
}
