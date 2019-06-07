package com.espweb.chronos.storage.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Exercicio {
    @Id
    private long id;

    private String descricao;
    private int quantidade;
    private int acertos;

    private ToOne<Assunto> assunto;

    public Exercicio(long id, String descricao, int quantidade, int acertos, long assuntoId) {
        this.id = id;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.acertos = acertos;
        this.assunto.setTargetId(assuntoId);
    }

    public Exercicio(String descricao, int quantidade, int acertos, long assuntoId) {
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.acertos = acertos;
        this.assunto.setTargetId(assuntoId);
    }

    public Exercicio() {
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

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getAcertos() {
        return acertos;
    }

    public void setAcertos(int acertos) {
        this.acertos = acertos;
    }

    public ToOne<Assunto> getAssunto() {
        return assunto;
    }

    public void setAssunto(ToOne<Assunto> assunto) {
        this.assunto = assunto;
    }
}
