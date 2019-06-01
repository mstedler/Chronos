package com.espweb.chronos.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * A sample model. Replace this with your own.
 */
public class Cronograma {

    @SerializedName("data_inicio")
    private Date inicio;
    @SerializedName("data_fim")
    private Date fim;
    @SerializedName("disciplinas")
    private List<Disciplina> disciplinas;

    public Cronograma() {

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
