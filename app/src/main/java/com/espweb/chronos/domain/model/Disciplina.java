package com.espweb.chronos.domain.model;

import java.util.List;

public class Disciplina {

    private long id;
    private String uuid;
    private String nome;
    private String descricao;
    private List<Assunto> assuntos;

    private long cronogramaId;

    public Disciplina() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public long getCronogramaId() {
        return cronogramaId;
    }

    public void setCronogramaId(long cronogramaId) {
        this.cronogramaId = cronogramaId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
