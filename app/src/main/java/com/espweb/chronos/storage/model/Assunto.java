package com.espweb.chronos.storage.model;

import java.util.List;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

@Entity
public class Assunto {

    @Id
    private long id;
    private String uuid;
    private String descricao;
    private String anotacao;

    @Backlink(to = "assunto")
    private ToMany<Material> materiais;
    @Backlink(to = "assunto")
    private ToMany<Revisao> revisoes;
    @Backlink(to = "assunto")
    private ToMany<Exercicio> exercicios;

    private ToOne<Disciplina> disciplina;

    public Assunto(long id, String uuid, String descricao, String anotacao, long disciplinaId) {
        this.id = id;
        this.uuid = uuid;
        this.descricao = descricao;
        this.anotacao = anotacao;
        this.disciplina.setTargetId(disciplinaId);
    }

    public Assunto(String uuid, String descricao, String anotacao, long disciplinaId) {
        this.descricao = descricao;
        this.uuid = uuid;
        this.anotacao = anotacao;
        this.disciplina.setTargetId(disciplinaId);
    }

    public Assunto() {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getAnotacao() {
        return anotacao;
    }

    public void setAnotacao(String anotacao) {
        this.anotacao = anotacao;
    }

    public ToMany<Material> getMateriais() {
        return materiais;
    }

    public void setMateriais(ToMany<Material> materiais) {
        this.materiais = materiais;
    }

    public ToMany<Revisao> getRevisoes() {
        return revisoes;
    }

    public void setRevisoes(ToMany<Revisao> revisoes) {
        this.revisoes = revisoes;
    }

    public ToMany<Exercicio> getExercicios() {
        return exercicios;
    }

    public void setExercicios(ToMany<Exercicio> exercicios) {
        this.exercicios = exercicios;
    }

    public ToOne<Disciplina> getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(ToOne<Disciplina> disciplina) {
        this.disciplina = disciplina;
    }
}
