package com.espweb.chronos.data;


import android.content.Context;
import android.util.Log;

import androidx.work.WorkManager;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.domain.exceptions.SignInFailedException;
import com.espweb.chronos.domain.exceptions.SignUpFailedException;
import com.espweb.chronos.domain.model.User;
import com.espweb.chronos.domain.repository.SessaoRepository;
import com.espweb.chronos.network.RestClient;
import com.espweb.chronos.network.converters.NetworkToStorageConverter;
import com.espweb.chronos.network.model.Error;
import com.espweb.chronos.network.model.Sessao;
import com.espweb.chronos.network.services.SessionService;
import com.espweb.chronos.network.utils.Connection;
import com.espweb.chronos.storage.boxes.SessaoBox;
import com.espweb.chronos.storage.converters.StorageToDomainConverter;
import com.espweb.chronos.storage.database.ObjectBox;
import com.espweb.chronos.workers.api.base.ApiWorker;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Response;

public class SessaoRepositoryImpl implements SessaoRepository {
    private static final String TAG = SessaoRepositoryImpl.class.getName();

    private Context context;
    private SessaoBox box;

    public SessaoRepositoryImpl(Context context) {
        this.context = context.getApplicationContext();
        box = new SessaoBox();
    }

    @Override
    public com.espweb.chronos.domain.model.Sessao getSessao() throws NotFoundException {
        com.espweb.chronos.storage.model.Sessao sessao;

        sessao = box.getActiveSession();

        if(sessao == null) {
            throw new NotFoundException("Não há sessão");
        }

        return StorageToDomainConverter.convert(sessao);
    }

    @Override
    public void refreshToken(String newToken) {
        com.espweb.chronos.storage.model.Sessao sessao = box.getActiveSession();
        sessao.setToken(newToken);
        box.put(sessao);
    }

    @Override
    public User getUser() throws NotFoundException {
        return StorageToDomainConverter.convert(box.getActiveSessionUser());
    }

    @Override
    public void resetPassword(String email) throws Exception {
        if(!Connection.isOnline()) {
            throw new Exception("Não há conexão com a internet.");
        }
        SessionService sessionService = RestClient.createService(SessionService.class);
        sessionService.resetPassword(email).execute();
    }

    @Override
    public void signUpUser(User user) throws SignUpFailedException {
        SessionService signUpService = RestClient.createService(SessionService.class);
        try {
            Response<com.espweb.chronos.network.model.User> response = signUpService.signUp(user.getName(), user.getEmail(), user.getPassword()).execute();
            if(!response.isSuccessful()) {
                throw new SignUpFailedException("Erro ao criar usuário, tente novamente mais tarde");
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            throw new SignUpFailedException(e.getMessage());
        }
    }

    @Override
    public User signInUser(User user) throws SignInFailedException {
        SessionService signInService = RestClient.createService(SessionService.class);
        try {
            Response<Sessao> response = signInService.signIn(user.getEmail(), user.getPassword()).execute();
            if (response.isSuccessful()) {
                Sessao sessao = response.body();
                com.espweb.chronos.storage.model.Sessao sSessao = NetworkToStorageConverter.convert(sessao);
                box.put(sSessao);
                return StorageToDomainConverter.convert(box.getActiveSessionUser());
            } else {
                throw new SignInFailedException("Email ou senha inválidos.");
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            throw new SignInFailedException(e.getMessage());
        }
    }

    @Override
    public void signOutUser() {
        ObjectBox.deleteDB();
        ObjectBox.init(context);
        WorkManager.getInstance(context).cancelAllWork();
        WorkManager.getInstance(context).cancelUniqueWork(ApiWorker.NAME);
        WorkManager.getInstance(context).pruneWork();
    }


}
