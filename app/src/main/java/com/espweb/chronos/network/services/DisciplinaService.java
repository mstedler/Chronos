package com.espweb.chronos.network.services;

import com.espweb.chronos.network.model.Disciplina;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DisciplinaService {
    @DELETE("disciplinas/{uuid}")
    Call<Disciplina> delete(@Path("uuid") String uuid);

    @FormUrlEncoded
    @POST("disciplinas")
    Call<Disciplina> create(@Field("nome") String nome,
                            @Field("descricao") String descricao,
                            @Field("cronograma_uuid") String cronogramaUuid,
                            @Field("fim") String fim);

    @FormUrlEncoded
    @PUT("disciplinas/{uuid}")
    Call<Disciplina> update(@Path("uuid") String uuid,
                            @Field("nome") String nome,
                            @Field("descricao") String descricao,
                            @Field("cronograma_uuid") String cronogramaUuid,
                            @Field("fim") String fim);
}
