package com.example.exafinmaestropokemon.services;

import com.example.exafinmaestropokemon.entities.Pokemon;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PokemonService {
    @GET("pokemons/N00038802")
    Call<List<Pokemon>> getAll();

    @POST("pokemons/N00038802/crear")
    Call<Pokemon> create(@Body Pokemon pokemon);
}
