package com.espweb.chronos.network.services;

import androidx.room.Delete;

import com.espweb.chronos.network.model.Cronograma;
import com.espweb.chronos.network.model.Cronogramas;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CronogramaService {

    @GET("cronograma/completos")
    Call<Cronogramas> getCronogramasCompletos();

    @FormUrlEncoded
    @POST("cronogramas")
    Call<Cronograma> create(@Field("titulo") String titulo,
                                      @Field("descricao") String descricao,
                                      @Field("inicio") String inicio,
                                      @Field("fim") String fim);

    @FormUrlEncoded
    @PUT("cronogramas/{uuid}")
    Call<Cronograma> update(@Path("uuid") String uuid,
                                      @Field("titulo") String titulo,
                                      @Field("descricao") String descricao,
                                      @Field("inicio") String inicio,
                                      @Field("fim") String fim);

    @DELETE("cronogramas/{uuid}")
    Call<Cronograma> delete(@Path("uuid") String uuid);

}
