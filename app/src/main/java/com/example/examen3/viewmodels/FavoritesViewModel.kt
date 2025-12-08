package com.example.examen3.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examen3.domain.model.Pokemon
import com.example.examen3.domain.usecase.GetFavoritesUseCase
import com.example.examen3.domain.usacase.RemoveFromFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
) : ViewModel() {

    private val _favorites = MutableStateFlow<List<Pokemon>>(emptyList())
    val favorites: StateFlow<List<Pokemon>> = _favorites.asStateFlow()

    private val _uiState = MutableStateFlow<FavoritesUiState>(FavoritesUiState.Loading)
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            _uiState.value = FavoritesUiState.Loading

            getFavoritesUseCase().collect { pokemonList ->
                _favorites.value = pokemonList
                _uiState.value = if (pokemonList.isEmpty()) {
                    FavoritesUiState.Empty
                } else {
                    FavoritesUiState.Success
                }
            }
        }
    }

    fun removeFavorite(pokemonId: Int) {
        viewModelScope.launch {
            removeFromFavoritesUseCase(pokemonId)
        }
    }
}

sealed class FavoritesUiState {
    object Loading : FavoritesUiState()
    object Success : FavoritesUiState()
    object Empty : FavoritesUiState()
}