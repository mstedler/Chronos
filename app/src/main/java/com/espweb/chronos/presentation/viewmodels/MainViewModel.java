package com.espweb.chronos.presentation.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.espweb.chronos.presentation.model.Cronograma;
import com.espweb.chronos.presentation.model.User;

import java.util.List;

public class MainViewModel extends ViewModel {
    private MutableLiveData<User> user = new MutableLiveData<>();
    private MutableLiveData<List<Cronograma>> cronogramas = new MutableLiveData<>();

    public LiveData<User> getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user.setValue(user);
    }

    public LiveData<List<Cronograma>> getCronogramas() {
        return cronogramas;
    }

    public void setCronogramas(List<Cronograma> cronogramas) {
        this.cronogramas.setValue(cronogramas);
    }
}
