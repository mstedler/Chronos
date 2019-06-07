package com.espweb.chronos.storage.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Revisao {
    @Id
    private long id;

    private int escopo;
    private int quantidade;

    private ToOne<Assunto> assunto;

    public Revisao(long id, int escopo, int quantidade, long assuntoId) {
        this.id = id;
        this.escopo = escopo;
        this.quantidade = quantidade;
        this.assunto.setTargetId(assuntoId);
    }

    public Revisao(int escopo, int quantidade, long assuntoId) {
        this.escopo = escopo;
        this.quantidade = quantidade;
        this.assunto.setTargetId(assuntoId);
    }

    public Revisao() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getEscopo() {
        return escopo;
    }

    public void setEscopo(int escopo) {
        this.escopo = escopo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public ToOne<Assunto> getAssunto() {
        return assunto;
    }

    public void setAssunto(ToOne<Assunto> assunto) {
        this.assunto = assunto;
    }
}
