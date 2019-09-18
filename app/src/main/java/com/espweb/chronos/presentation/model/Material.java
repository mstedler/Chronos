package com.espweb.chronos.presentation.model;

import com.espweb.chronos.domain.model.EnumTipo;

import org.parceler.Parcel;

@Parcel(Parcel.Serialization.BEAN)
public class Material extends Artefato {
    private int minutos;

    public enum Escopo {
        VIDEO_AULA(1),
        LIVROS(2),
        DIVERSOS(3);
        private int value;

        public int getIntValue() {
            return value;
        }

        Escopo(int value) {
            this.value = value;
        }

        public static Escopo fromInt(int i) {
            for (Escopo b : Escopo.values()) {
                if (b.getIntValue() == i) {
                    return b;
                }
            }
            return null;
        }
    }

    private Escopo escopo = Escopo.VIDEO_AULA;

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

    @Override
    public EnumTipo getTipo() {
        return EnumTipo.MATERIAL;
    }

    public boolean isMinutosValid() {
        return minutos > 0;
    }

    public Escopo getEscopo() {
        return escopo;
    }

    public void setEscopo(Escopo escopo) {
        this.escopo = escopo;
    }
}
