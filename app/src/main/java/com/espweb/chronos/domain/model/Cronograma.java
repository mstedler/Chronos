package com.espweb.chronos.domain.model;

import java.util.Date;
import java.util.List;

/**
 * A sample model. Replace this with your own.
 */
public class Cronograma {

    private Date inicio;
    private Date fim;
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
