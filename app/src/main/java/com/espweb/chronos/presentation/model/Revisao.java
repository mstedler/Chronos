package com.espweb.chronos.presentation.model;

import com.espweb.chronos.domain.model.EnumTipo;

import org.parceler.Parcel;

@Parcel(Parcel.Serialization.BEAN)
public class Revisao extends Artefato {
    public Revisao(long idAssunto) {
        this.idAssunto = idAssunto;
    }

    public enum Escopo {
        DIARIA(0),
        SEMANAL(1),
        QUINZENAL(2),
        MENSAL(3);

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

    private Escopo escopo = Escopo.DIARIA;

    public Revisao() {
    }

    public Escopo getEscopo() {
        return escopo;
    }

    public void setEscopo(Escopo escopo) {
        this.escopo = escopo;
    }

    @Override
    public EnumTipo getTipo() {
        return EnumTipo.REVISAO;
    }
}
