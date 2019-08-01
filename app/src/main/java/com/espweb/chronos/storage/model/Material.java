package com.espweb.chronos.storage.model;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.relation.ToOne;

@Entity
public class Material extends Artefato{
    private int minutos;

    private ToOne<Assunto> assunto;

    public Material(long id, String uuid, Date data, String descricao, int minutos, long assuntoId) {
        this.id = id;
        this.uuid = uuid;
        this.data = data;
        this.descricao = descricao;
        this.minutos = minutos;
        this.assunto.setTargetId(assuntoId);
    }

    public Material(String uuid, Date data, String descricao, int minutos, long assuntoId) {
        this.uuid = uuid;
        this.data = data;
        this.descricao = descricao;
        this.minutos = minutos;
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
}
