package com.espweb.chronos.storage.model;

import java.util.Date;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class Cronograma {

    @Id
    private long id;

    private String uuid;

    private String titulo;
    private String descricao;
    private Date inicio;
    private Date fim;
    private boolean synced;

    @Backlink(to = "cronograma")
    private ToMany<Disciplina> disciplinas;

    public Cronograma(long id, String uuid, String titulo, String descricao, Date inicio, Date fim, boolean synced) {
        this.id = id;
        this.uuid = uuid;
        this.titulo = titulo;
        this.descricao = descricao;
        this.inicio = inicio;
        this.fim = fim;
        this.synced = synced;
    }

    public boolean isSynced() {
        return synced;
    }

    public void setSynced(boolean synced) {
        this.synced = synced;
    }

    public Cronograma() {

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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    public ToMany<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(ToMany<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }
}
