package com.espweb.chronos.network.services;

import com.espweb.chronos.network.model.Cronogramas;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CronogramaService {
    @GET("cronograma/completos")
    Call<Cronogramas> getAllCronogramas();
}
