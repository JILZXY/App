package com.example.examen3.domain.usacase

import com.example.examen3.domain.model.Pokemon
import com.example.examen3.domain.repository.PokemonRepository
import javax.inject.Inject

class AddToFavoritesUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(pokemon: Pokemon) {
        repository.addToFavorites(pokemon)
    }
}