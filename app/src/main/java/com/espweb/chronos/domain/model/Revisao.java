package com.espweb.chronos.domain.model;

public class Revisao {

    private long id;

    public enum Escopo {
        DIARIA(0),
        SEMANAL(1),
        QUINZENAL(2),
        MENSAL(3);

        private int value;

        public int getIntValue() {
            return value;
        }

        Escopo(int value){
            this.value = value;
        }
        public static Escopo fromInt(int i) {
            for (Escopo b : Escopo.values()) {
                if (b.getIntValue() == i) { return b; }
            }
            return null;
        }
    }
    private Escopo escopo;
    private int quantidade;

    public Revisao() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
