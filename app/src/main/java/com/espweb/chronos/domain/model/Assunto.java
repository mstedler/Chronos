package com.espweb.chronos.domain.model;

import java.util.List;

public class Assunto {
    private long id;
    private String uuid;
    private String descricao;
    private List<Artefato> artefatos;

    private long disciplinaId;

    public Assunto() {
    }

    public Assunto(long disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public List<Artefato> getArtefatos() {
        return artefatos;
    }

    public void setArtefatos(List<Artefato> artefatos) {
        this.artefatos = artefatos;
    }

    public long getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public long getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(long disciplinaId) {
        this.disciplinaId = disciplinaId;
    }
}
