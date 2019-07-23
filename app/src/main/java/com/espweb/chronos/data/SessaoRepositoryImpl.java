package com.espweb.chronos.data;


import android.content.Context;
import android.util.Log;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.domain.exceptions.SignInFailedException;
import com.espweb.chronos.domain.exceptions.SignUpFailedException;
import com.espweb.chronos.domain.model.User;
import com.espweb.chronos.domain.repository.SessaoRepository;
import com.espweb.chronos.network.RestClient;
import com.espweb.chronos.network.converters.RESTModelConverter;
import com.espweb.chronos.network.model.Error;
import com.espweb.chronos.network.model.Sessao;
import com.espweb.chronos.network.services.SessionService;
import com.espweb.chronos.storage.converters.StorageToDomainConverter;
import com.espweb.chronos.storage.database.ObjectBox;
import com.espweb.chronos.storage.model.Sessao_;
import com.google.gson.Gson;

import java.io.IOException;

import io.objectbox.Box;
import retrofit2.Response;

public class SessaoRepositoryImpl implements SessaoRepository {
    private static final String TAG = SessaoRepositoryImpl.class.getName();

    private Context context;

    public SessaoRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public com.espweb.chronos.domain.model.Sessao getSessao() throws NotFoundException {
        com.espweb.chronos.storage.model.Sessao sessao;

        sessao = getBox().query().equal(Sessao_.active, true).build().findFirst();

        if(sessao == null) {
            throw new NotFoundException("Não há sessão");
        }

        return StorageToDomainConverter.convertSessaoToDomainModel(sessao);
    }

    @Override
    public void refreshToken(String newToken) {
        com.espweb.chronos.storage.model.Sessao sessao = getBox().query().equal(Sessao_.active, true).build().findFirst();
        sessao.setToken(newToken);
        getBox().put(sessao);
    }

    @Override
    public User getUser() throws NotFoundException {
        return getSessao().getUser();
    }

    @Override
    public void saveSessao(com.espweb.chronos.domain.model.Sessao sessao) {
        com.espweb.chronos.storage.model.Sessao sSessao = new com.espweb.chronos.storage.model.Sessao();
        com.espweb.chronos.storage.model.User sUser = new com.espweb.chronos.storage.model.User();
        sUser.setName(sessao.getUser().getName());
        sUser.setEmail(sessao.getUser().getEmail());
        sUser.setUpdatedAt(sessao.getUser().getUpdatedAt());
        sUser.setCreatedAt(sessao.getUser().getCreatedAt());
        sUser.setUuid(sessao.getUser().getUuid());
        sSessao.setToken(sessao.getToken());
        sSessao.setActive(true);
        sSessao.getUser().setTarget(sUser);
        getBox().put(sSessao);
    }

    @Override
    public void signUpUser(User user) throws SignUpFailedException {
        SessionService signUpService = RestClient.createService(SessionService.class);
        try {
            Response<com.espweb.chronos.network.model.User> response = signUpService.signUp(user.getName(), user.getEmail(), user.getPassword()).execute();
            if (!response.isSuccessful()) {
                Gson gson = new Gson();
                Error error = gson.fromJson(response.errorBody().charStream(), Error.class);
                throw new SignUpFailedException(error.getMessage());
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            throw new SignUpFailedException(e.getMessage());
        }
    }

    @Override
    public com.espweb.chronos.domain.model.Sessao signInUser(User user) throws SignInFailedException {
        SessionService signInService = RestClient.createService(SessionService.class);
        try {
            Response<Sessao> response = signInService.signIn(user.getEmail(), user.getPassword()).execute();
            if (response.isSuccessful()) {
                return RESTModelConverter.convertSessaoToDomainModel(response.body());
            } else {
                Gson gson = new Gson();
                Error error = gson.fromJson(response.errorBody().charStream(), Error.class);
                throw new SignInFailedException(error.getMessage());
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            throw new SignInFailedException(e.getMessage());
        }
    }

    @Override
    public void signOutUser() {
        ObjectBox.deleteDB(context.getApplicationContext());
    }

    private Box<com.espweb.chronos.storage.model.Sessao> getBox() {
        return ObjectBox.get().boxFor(com.espweb.chronos.storage.model.Sessao.class);
    }
}
