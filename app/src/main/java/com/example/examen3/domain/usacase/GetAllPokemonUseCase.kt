package com.example.examen3.domain.usacase

import com.example.examen3.domain.model.Pokemon
import com.example.examen3.domain.repository.PokemonRepository
import com.example.examen3.utils.NetworkResult
import javax.inject.Inject

class GetAllPokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(limit: Int, offset: Int): NetworkResult<List<Pokemon>> {
        return repository.getPokemonList(limit, offset)
    }
}