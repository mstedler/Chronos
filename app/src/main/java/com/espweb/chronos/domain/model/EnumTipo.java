package com.espweb.chronos.domain.model;

public enum EnumTipo {
    MATERIAL(0),
    REVISAO(1),
    EXERCICIO(2);

    private int value;

    public int getIntValue() {
        return value;
    }

    EnumTipo(int value) {
        this.value = value;
    }

    public static EnumTipo fromInt(int i) {
        for (EnumTipo b : EnumTipo.values()) {
            if (b.getIntValue() == i) {
                return b;
            }
        }
        return EXERCICIO;
    }
}