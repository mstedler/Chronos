package com.espweb.chronos.storage.model;

import java.util.Date;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Unique;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

@Entity
public class Disciplina {

    @Id
    private long id;

    @Unique
    private String uuid;

    private String nome;

    private String descricao;

    @Backlink(to = "disciplina")
    private ToMany<Assunto> assuntos;

    private ToOne<Cronograma> cronograma;

    private Date deletedAt;

    public Disciplina(String uuid, String nome, String descricao, long cronogramaId) {
        this.uuid = uuid;
        this.nome = nome;
        this.descricao = descricao;
        this.cronograma.setTargetId(cronogramaId);
    }

    public Disciplina(long id, String uuid, String nome, String descricao, long cronogramaId) {
        this.id = id;
        this.uuid = uuid;
        this.nome = nome;
        this.descricao = descricao;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
}
