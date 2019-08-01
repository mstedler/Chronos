package com.espweb.chronos.storage.model;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Exercicio extends Artefato{
    private int quantidade;
    private int acertos;

    private ToOne<Assunto> assunto;

    public Exercicio(long id, String uuid, Date data, String descricao, int quantidade, int acertos, long assuntoId) {
        this.id = id;
        this.uuid = uuid;
        this.data = data;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.acertos = acertos;
        this.assunto.setTargetId(assuntoId);
    }

    public Exercicio(String uuid, Date data, String descricao, int quantidade, int acertos, long assuntoId) {
        this.uuid = uuid;
        this.data = data;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.acertos = acertos;
        this.assunto.setTargetId(assuntoId);
    }

    public Exercicio() {
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
