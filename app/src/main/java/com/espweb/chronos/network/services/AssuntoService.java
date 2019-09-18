package com.espweb.chronos.network.services;

import com.espweb.chronos.network.model.Assunto;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AssuntoService {
    @FormUrlEncoded
    @POST("assuntos")
    Call<Assunto> create(@Field("descricao") String descricao, @Field("anotacao") String anotacao, @Field("disciplina_uuid") String disciplinaUuid);

    @FormUrlEncoded
    @PUT("assuntos/{uuid}")
    Call<Assunto> update(@Path ("uuid") String uuid, @Field("descricao") String descricao, @Field("anotacao") String anotacao, @Field("disciplina_uuid") String disciplinaUuid);

    @DELETE("assuntos/{uuid}")
    Call<Assunto> delete(@Path ("uuid") String uuid);
}
