package com.espweb.chronos.domain.model;

import java.util.List;

public class Disciplina {

    private String nome;
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
