package com.espweb.chronos.network.converters;

import com.espweb.chronos.domain.model.Sessao;
import com.espweb.chronos.domain.model.User;

import java.util.Collections;

public class NetworkToDomainConverter {
    public static User convert(com.espweb.chronos.network.model.User nUser) {
        User user = new User();
        user.setUuid(nUser.getUuid());
        user.setName(nUser.getName());
        user.setEmail(nUser.getEmail());
        user.setPassword(nUser.getPassword());
        user.setCreatedAt(nUser.getCreatedAt());
        user.setUpdatedAt(nUser.getUpdatedAt());
        user.setCronogramas(Collections.emptyList());
        return user;
    }

    public static Sessao convert(com.espweb.chronos.network.model.Sessao nSessao) {
        Sessao sessao = new Sessao();
        sessao.setToken(nSessao.getToken());
        sessao.setUser(convert(nSessao.getUser()));
        return sessao;
    }
}
