package com.example.examen3.domain.usacase

import com.example.examen3.domain.repository.PokemonRepository
import javax.inject.Inject

class RemoveFromFavoritesUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(pokemonId: Int) {
        repository.removeFromFavorites(pokemonId)
    }
}