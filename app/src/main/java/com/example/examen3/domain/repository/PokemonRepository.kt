package com.example.examen3.domain.repository

import com.example.examen3.domain.model.Pokemon
import com.example.examen3.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun getPokemonList(limit: Int, offset: Int): NetworkResult<List<Pokemon>>
    suspend fun getPokemonDetail(id: Int): NetworkResult<Pokemon>
    suspend fun searchPokemon(query: String): NetworkResult<Pokemon>
    suspend fun addToFavorites(pokemon: Pokemon)
    suspend fun removeFromFavorites(pokemonId: Int)
    fun getFavorites(): Flow<List<Pokemon>>
    suspend fun isFavorite(pokemonId: Int): Boolean
    suspend fun getFavoriteById(pokemonId: Int): Pokemon?
}