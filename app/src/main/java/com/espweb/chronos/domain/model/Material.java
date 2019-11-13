package com.espweb.chronos.domain.model;

public class Material extends Artefato{

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
    private Escopo escopo;
    private int minutos;

    private boolean comecarAgora;

    public Material() {
    }

    @Override
    public EnumTipo getTipo() {
        return EnumTipo.MATERIAL;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public Escopo getEscopo() {
        return escopo;
    }

    public void setEscopo(Escopo escopo) {
        this.escopo = escopo;
    }

    public boolean isComecarAgora() {
        return comecarAgora;
    }

    public void setComecarAgora(boolean comecarAgora) {
        this.comecarAgora = comecarAgora;
    }
}
