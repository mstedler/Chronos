package com.espweb.chronos.presentation.model;

import org.parceler.Parcel;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Parcel(Parcel.Serialization.BEAN)
public class User {
    private long id;

    private String name;

    private String email;

    private String password;

    private String uuid;

    private Date createdAt;

    private Date updatedAt;

    private List<Cronograma> cronogramas;

    public User() {
    }

    public User(long id, String name, String email, String password, String uuid, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.uuid = uuid;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCronogramas(List<Cronograma> cronogramas) {
        this.cronogramas = cronogramas;
    }

    public List<Cronograma> getCronogramas() {
        return cronogramas;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
