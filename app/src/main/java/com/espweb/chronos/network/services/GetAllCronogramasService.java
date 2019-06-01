package com.espweb.chronos.network.services;
import com.espweb.chronos.network.model.GetAllCronogramas;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetAllCronogramasService {

    @GET("/getAllCronogramas")
    Call<GetAllCronogramas> getAllCronogramas(@Query("user_id") int userId);
}
