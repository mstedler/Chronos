package com.espweb.chronos.storage.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Sessao {
    @Id
    long id;

    String token;

    ToOne<User> user;

    boolean active;

    public Sessao() {

    }

    public Sessao(long id, String token, boolean active, long userId) {
        this.id = id;
        this.token = token;
        this.active = active;
        this.user.setTargetId(userId);
    }

    public void setUser(ToOne<User> user) {
        this.user = user;
    }

    public ToOne<User> getUser() {
        return user;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
