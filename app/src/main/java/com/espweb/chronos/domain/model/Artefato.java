package com.espweb.chronos.domain.model;

import java.util.Date;

public abstract class Artefato {
    private long id;
    private String uuid;
    private Date data;

    public Artefato() {
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
