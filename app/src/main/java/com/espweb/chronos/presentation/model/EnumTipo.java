package com.espweb.chronos.presentation.model;

public enum EnumTipo {
    EXERCICIO(0),
    MATERIAL(1),
    REVISAO(2);

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