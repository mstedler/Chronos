package com.espweb.chronos.presentation.model;

import org.parceler.Parcel;

import java.util.Collections;
import java.util.List;

@Parcel(Parcel.Serialization.BEAN)
public class Disciplina {

    private long id;
    private String uuid;
    private String nome;
    private String descricao;
    private List<Assunto> assuntos = Collections.emptyList();

    private long idCronograma;

    public Disciplina() {
    }

    public Disciplina(long idCronograma) {
        this.idCronograma = idCronograma;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Assunto> getAssuntos() {
        return assuntos;
    }

    public void setAssuntos(List<Assunto> assuntos) {
        this.assuntos = assuntos;
    }

    public long getIdCronograma() {
        return idCronograma;
    }

    public void setIdCronograma(long idCronograma) {
        this.idCronograma = idCronograma;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isNew() {
        return id == 0;
    }

    public boolean isNomeValid() {
        return nome.trim().length() > 3;
    }

    public boolean isDescricaoValid() {
        return descricao.trim().length() > 3;
    }

    public int getAssuntosSize() {
        return assuntos.size();
    }
}
