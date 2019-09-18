package com.espweb.chronos.storage.model;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.relation.ToOne;

@Entity
public class Material extends Artefato{
    private int minutos;
    private int escopo;
    private ToOne<Assunto> assunto;

    public Material(long id, String uuid, Date data, String descricao, int minutos, int escopo, long assuntoId) {
        this.id = id;
        this.uuid = uuid;
        this.data = data;
        this.descricao = descricao;
        this.minutos = minutos;
        this.escopo = escopo;
        this.assunto.setTargetId(assuntoId);
    }

    public Material(String uuid, Date data, String descricao, int minutos, int escopo, long assuntoId) {
        this.uuid = uuid;
        this.data = data;
        this.descricao = descricao;
        this.minutos = minutos;
        this.escopo = escopo;
        this.assunto.setTargetId(assuntoId);
    }

    public Material() {
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public ToOne<Assunto> getAssunto() {
        return assunto;
    }

    public void setAssunto(ToOne<Assunto> assunto) {
        this.assunto = assunto;
    }

    public int getEscopo() {
        return escopo;
    }

    public void setEscopo(int escopo) {
        this.escopo = escopo;
    }
}
