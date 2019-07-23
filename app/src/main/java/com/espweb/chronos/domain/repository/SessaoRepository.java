package com.espweb.chronos.domain.repository;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.domain.exceptions.SignInFailedException;
import com.espweb.chronos.domain.exceptions.SignUpFailedException;
import com.espweb.chronos.domain.model.Sessao;
import com.espweb.chronos.domain.model.User;

import java.io.IOException;

public interface SessaoRepository {
    void signUpUser(User user) throws SignUpFailedException;
    Sessao signInUser(User user) throws SignInFailedException;
    void signOutUser();
    Sessao getSessao() throws NotFoundException;
    void refreshToken(String newToken);
    User getUser() throws NotFoundException;
    void saveSessao(Sessao sessao);
}
