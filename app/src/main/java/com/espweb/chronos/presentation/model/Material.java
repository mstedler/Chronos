package com.espweb.chronos.presentation.model;

import org.parceler.Parcel;

@Parcel(Parcel.Serialization.BEAN)
public class Material extends Artefato {
    private int minutos;

    public Material() {
    }

    public Material(long idAssunto) {
        this.idAssunto = idAssunto;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }
}
