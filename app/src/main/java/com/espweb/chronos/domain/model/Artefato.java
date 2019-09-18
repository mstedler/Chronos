package com.espweb.chronos.domain.model;

import java.util.Date;

public abstract class Artefato {
    private long id;
    private String uuid;
    private Date data;
    private String descricao;

    protected long idAssunto;

    public Artefato() {
    }

    public Artefato(long idAssunto) {
        this.idAssunto = idAssunto;
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public long getIdAssunto() {
        return idAssunto;
    }

    public void setIdAssunto(long idAssunto) {
        this.idAssunto = idAssunto;
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

    public abstract EnumTipo getTipo();
}
