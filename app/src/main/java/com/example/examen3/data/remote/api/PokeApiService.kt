package com.example.examen3.data.remote.api

import com.example.examen3.data.remote.dto.PokemonDetailResponse
import com.example.examen3.data.remote.dto.PokemonListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<PokemonListResponse>

    @GET("pokemon/{id}")
    suspend fun getPokemonById(
        @Path("id") id: Int
    ): Response<PokemonDetailResponse>

    @GET("pokemon/{name}")
    suspend fun getPokemonByName(
        @Path("name") name: String
    ): Response<PokemonDetailResponse>
}