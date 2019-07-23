package com.espweb.chronos.storage.model;

import java.util.Date;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Unique;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

@Entity
public class Cronograma {

    @Id
    private long id;

    @Unique
    private String uuid;

    private String titulo;
    private String descricao;
    private Date inicio;
    private Date fim;

    @Backlink(to = "cronograma")
    private ToMany<Disciplina> disciplinas;

    private ToOne<User> user;

    public Cronograma(long id, String uuid, String titulo, String descricao, Date inicio, Date fim, long userId) {
        this.id = id;
        this.uuid = uuid;
        this.titulo = titulo;
        this.descricao = descricao;
        this.inicio = inicio;
        this.fim = fim;
        this.user.setTargetId(userId);
    }

    public Cronograma() {

    }

    public ToOne<User> getUser() {
        return user;
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
