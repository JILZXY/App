package com.example.examen3.domain.usecase

import com.example.examen3.domain.repository.PokemonRepository
import javax.inject.Inject

class IsFavoriteUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(pokemonId: Int): Boolean {
        return repository.isFavorite(pokemonId)
    }
}