package com.example.examen3.domain.usecase

import com.example.examen3.domain.model.Pokemon
import com.example.examen3.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    operator fun invoke(): Flow<List<Pokemon>> {
        return repository.getFavorites()
    }
}