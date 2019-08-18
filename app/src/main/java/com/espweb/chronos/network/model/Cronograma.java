package com.espweb.chronos.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Cronograma {

    @SerializedName("cronograma")
    private Cronograma cronograma;

    @SerializedName("uuid")
    private String uuid;
    @SerializedName("titulo")
    private String titulo;
    @SerializedName("descricao")
    private String descricao;
    @SerializedName("inicio")
    private Date inicio;
    @SerializedName("fim")
    private Date fim;
    @SerializedName("disciplinas")
    private List<Disciplina> disciplinas;

    public Cronograma() {

    }

    public Cronograma getCronograma() {
        return cronograma;
    }

    public void setCronograma(Cronograma cronograma) {
        this.cronograma = cronograma;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
