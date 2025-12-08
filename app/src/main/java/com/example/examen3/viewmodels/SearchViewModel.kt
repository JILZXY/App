package com.example.examen3.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examen3.domain.model.Pokemon
import com.example.examen3.domain.usacase.SearchPokemonUseCase
import com.example.examen3.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchPokemonUseCase: SearchPokemonUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Empty)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private var searchJob: Job? = null

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query

        searchJob?.cancel()

        if (query.isEmpty()) {
            _uiState.value = SearchUiState.Empty
            return
        }

        if (query.length < 2) {
            return
        }

        searchJob = viewModelScope.launch {
            delay(500)
            searchPokemon(query)
        }
    }

    fun searchPokemon(query: String) {
        if (query.isBlank()) {
            _uiState.value = SearchUiState.Empty
            return
        }

        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading

            when (val result = searchPokemonUseCase(query.lowercase().trim())) {
                is NetworkResult.Success -> {
                    result.data?.let { pokemon ->
                        _uiState.value = SearchUiState.Success(pokemon)
                    } ?: run {
                        _uiState.value = SearchUiState.Error("Pokémon no encontrado")
                    }
                }
                is NetworkResult.Error -> {
                    _uiState.value = SearchUiState.Error(result.message ?: "Error en la búsqueda")
                }
                is NetworkResult.Loading -> {
                    _uiState.value = SearchUiState.Loading
                }
            }
        }
    }

    fun clearSearch() {
        _searchQuery.value = ""
        _uiState.value = SearchUiState.Empty
        searchJob?.cancel()
    }
}

sealed class SearchUiState {
    object Empty : SearchUiState()
    object Loading : SearchUiState()
    data class Success(val pokemon: Pokemon) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}