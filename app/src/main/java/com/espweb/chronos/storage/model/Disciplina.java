package com.espweb.chronos.storage.model;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

@Entity
public class Disciplina {

    @Id
    private long id;
    private String uuid;
    private String nome;

    @Backlink(to = "disciplina")
    private ToMany<Assunto> assuntos;

    private ToOne<Cronograma> cronograma;

    public Disciplina(String uuid, String nome, long cronogramaId) {
        this.uuid = uuid;
        this.nome = nome;
        this.cronograma.setTargetId(cronogramaId);
    }

    public Disciplina(long id, String uuid, String nome, long cronogramaId) {
        this.id = id;
        this.uuid = uuid;
        this.nome = nome;
        this.cronograma.setTargetId(cronogramaId);
    }

    public Disciplina() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ToMany<Assunto> getAssuntos() {
        return assuntos;
    }

    public void setAssuntos(ToMany<Assunto> assuntos) {
        this.assuntos = assuntos;
    }

    public ToOne<Cronograma> getCronograma() {
        return cronograma;
    }

    public void setCronograma(ToOne<Cronograma> cronograma) {
        this.cronograma = cronograma;
    }
}
