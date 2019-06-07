package com.espweb.chronos.storage.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Material {
    @Id
    private long id;

    private String descricao;
    private int porcentagem;


    private ToOne<Assunto> assunto;

    public Material(long id, String descricao, int porcentagem, long assuntoId) {
        this.id = id;
        this.descricao = descricao;
        this.porcentagem = porcentagem;
        this.assunto.setTargetId(assuntoId);
    }

    public Material(String descricao, int porcentagem, long assuntoId) {
        this.descricao = descricao;
        this.porcentagem = porcentagem;
        this.assunto.setTargetId(assuntoId);
    }

    public Material() {
    }

    public long getId() {
        return id;
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

    public int getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(int porcentagem) {
        this.porcentagem = porcentagem;
    }

    public ToOne<Assunto> getAssunto() {
        return assunto;
    }

    public void setAssunto(ToOne<Assunto> assunto) {
        this.assunto = assunto;
    }
}
