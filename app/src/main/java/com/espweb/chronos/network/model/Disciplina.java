package com.espweb.chronos.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Disciplina {

    @SerializedName("nome")
    private String nome;
    @SerializedName("assuntos")
    private List<Assunto> assuntos;

    public Disciplina() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Assunto> getAssuntos() {
        return assuntos;
    }

    public void setAssuntos(List<Assunto> assuntos) {
        this.assuntos = assuntos;
    }
}
