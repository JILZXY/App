package com.example.examen3.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examen3.domain.model.Pokemon
import com.example.examen3.domain.usacase.AddToFavoritesUseCase
import com.example.examen3.domain.usacase.GetPokemonDetailUseCase
import com.example.examen3.domain.usacase.RemoveFromFavoritesUseCase
import com.example.examen3.domain.usecase.IsFavoriteUseCase
import com.example.examen3.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    fun loadPokemonDetail(pokemonId: Int) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading

            when (val result = getPokemonDetailUseCase(pokemonId)) {
                is NetworkResult.Success -> {
                    result.data?.let { pokemon ->
                        _uiState.value = DetailUiState.Success(pokemon)
                        checkIfFavorite(pokemonId)
                    } ?: run {
                        _uiState.value = DetailUiState.Error("Pokémon no encontrado")
                    }
                }
                is NetworkResult.Error -> {
                    _uiState.value = DetailUiState.Error(result.message ?: "Error desconocido")
                }
                is NetworkResult.Loading -> {
                    _uiState.value = DetailUiState.Loading
                }
            }
        }
    }

    private fun checkIfFavorite(pokemonId: Int) {
        viewModelScope.launch {
            _isFavorite.value = isFavoriteUseCase(pokemonId)
        }
    }

    fun toggleFavorite(pokemon: Pokemon) {
        viewModelScope.launch {
            if (_isFavorite.value) {
                removeFromFavoritesUseCase(pokemon.id)
                _isFavorite.value = false
            } else {
                addToFavoritesUseCase(pokemon)
                _isFavorite.value = true
            }
        }
    }
}

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val pokemon: Pokemon) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}