package com.espweb.chronos.storage.model;

import java.util.Date;

import io.objectbox.annotation.BaseEntity;
import io.objectbox.annotation.Id;

@BaseEntity
public class Artefato {
    @Id
    long id;

    String uuid;

    Date data;

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
}
