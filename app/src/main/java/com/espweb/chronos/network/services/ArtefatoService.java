package com.espweb.chronos.network.services;

import com.espweb.chronos.network.model.Artefato;
import com.espweb.chronos.network.model.Assunto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ArtefatoService {

    @POST("artefatos")
    Call<Artefato> create(@Body Artefato artefato);

    @PUT("artefatos/{uuid}")
    Call<Artefato> update(@Body Artefato artefato, @Path("uuid") String uuid);

    @DELETE("artefatos/revisao/{uuid}")
    Call<Artefato> deleteRevisao(@Path ("uuid") String uuid);

    @DELETE("artefatos/material/{uuid}")
    Call<Artefato> deleteMaterial(@Path ("uuid") String uuid);

    @DELETE("artefatos/exercicio/{uuid}")
    Call<Artefato> deleteExercicio(@Path ("uuid") String uuid);
}
