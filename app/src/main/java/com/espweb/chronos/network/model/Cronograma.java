package com.espweb.chronos.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Cronograma {

    @SerializedName("uid")
    private String uid;
    @SerializedName("titulo")
    private String titulo;
    @SerializedName("descricao")
    private String descricao;
    @SerializedName("data_inicio")
    private Date inicio;
    @SerializedName("data_fim")
    private Date fim;
    @SerializedName("disciplinas")
    private List<Disciplina> disciplinas;

    public Cronograma() {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
}
