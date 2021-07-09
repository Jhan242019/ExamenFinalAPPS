package com.example.exafinmaestropokemon.services;

import com.example.exafinmaestropokemon.entities.Entrenador;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface EntrenadorService {
    @GET("entrenador/N00038802")
    Call<Entrenador> getAll();

    @POST("entrenador/N00038802")
    Call<Entrenador> create(@Body Entrenador entrenador);
}
