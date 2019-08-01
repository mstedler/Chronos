package com.espweb.chronos.storage.model;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Revisao extends Artefato{
    private int escopo;
    private ToOne<Assunto> assunto;

    public Revisao(long id, String uuid, Date data, int escopo, String descricao, long assuntoId) {
        this.id = id;
        this.uuid = uuid;
        this.data = data;
        this.escopo = escopo;
        this.descricao = descricao;
        this.assunto.setTargetId(assuntoId);
    }

    public Revisao(String uuid, Date data, int escopo, String descricao, long assuntoId) {
        this.uuid = uuid;
        this.data = data;
        this.escopo = escopo;
        this.descricao = descricao;
        this.assunto.setTargetId(assuntoId);
    }

    public Revisao() {
    }

    public int getEscopo() {
        return escopo;
    }

    public void setEscopo(int escopo) {
        this.escopo = escopo;
    }

    public ToOne<Assunto> getAssunto() {
        return assunto;
    }

    public void setAssunto(ToOne<Assunto> assunto) {
        this.assunto = assunto;
    }
}
