package com.espweb.chronos.presentation.model;

import androidx.annotation.NonNull;

import org.parceler.Parcel;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Parcel(Parcel.Serialization.BEAN)
public class Cronograma {

    private long id;
    private String uuid;
    private String titulo;
    private String descricao;
    private Date inicio;
    private Date fim;
    private List<Disciplina> disciplinas = Collections.emptyList();

    private long userId;

    public Cronograma(long userId) {
        this.userId = userId;
    }

    public Cronograma(long id, String uuid, String titulo, String descricao, Date inicio, Date fim) {
        this.id = id;
        this.uuid = uuid;
        this.titulo = titulo;
        this.descricao = descricao;
        this.inicio = inicio;
        this.fim = fim;
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

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isNew() {
        return id == 0;
    }

    public boolean isTituloValid() {
        return titulo.trim().length() > 3;
    }

    @NonNull
    @Override
    public String toString() {
        return titulo;
    }
}
