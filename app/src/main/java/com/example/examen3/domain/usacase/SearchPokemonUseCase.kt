package com.example.examen3.domain.usacase

import com.example.examen3.domain.model.Pokemon
import com.example.examen3.domain.repository.PokemonRepository
import com.example.examen3.utils.NetworkResult
import javax.inject.Inject

class SearchPokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(query: String): NetworkResult<Pokemon> {
        if (query.isBlank()) {
            return NetworkResult.Error("La búsqueda no puede estar vacía")
        }
        return repository.searchPokemon(query.trim())
    }
}