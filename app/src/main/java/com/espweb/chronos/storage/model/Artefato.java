package com.espweb.chronos.storage.model;

import java.util.Date;

import io.objectbox.annotation.BaseEntity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Unique;

@BaseEntity
public class Artefato {
    @Id
    long id;

    @Unique
    String uuid;

    Date data;

    String descricao;

    private Date deletedAt;

    Artefato() {

    }
    public Artefato(long id, String uuid, Date date) {
        this.id = id;
        this.uuid = uuid;
        this.data = date;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
}
