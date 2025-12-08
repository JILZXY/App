package com.example.examen3.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examen3.domain.model.Pokemon
import com.example.examen3.domain.usacase.GetAllPokemonUseCase
import com.example.examen3.domain.usacase.GetPokemonDetailUseCase
import com.example.examen3.utils.Constants
import com.example.examen3.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllPokemonUseCase: GetAllPokemonUseCase,
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _pokemonList = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemonList: StateFlow<List<Pokemon>> = _pokemonList.asStateFlow()

    private var currentOffset = 0
    private var isLoadingMore = false

    init {
        loadPokemon()
    }

    fun loadPokemon() {
        if (isLoadingMore) return

        viewModelScope.launch {
            if (currentOffset == 0) {
                _uiState.value = HomeUiState.Loading
            }

            isLoadingMore = true

            when (val result = getAllPokemonUseCase(Constants.PAGE_SIZE, currentOffset)) {
                is NetworkResult.Success -> {
                    val basicList = result.data ?: emptyList()
                    val detailedList = mutableListOf<Pokemon>()

                    basicList.forEach { pokemon ->
                        when (val detailResult = getPokemonDetailUseCase(pokemon.id)) {
                            is NetworkResult.Success -> {
                                detailResult.data?.let { detailedList.add(it) }
                            }
                            else -> {
                                detailedList.add(pokemon)
                            }
                        }
                    }

                    _pokemonList.value = _pokemonList.value + detailedList
                    currentOffset += Constants.PAGE_SIZE
                    _uiState.value = HomeUiState.Success
                }
                is NetworkResult.Error -> {
                    if (currentOffset == 0) {
                        _uiState.value = HomeUiState.Error(result.message ?: "Error desconocido")
                    }
                }
                is NetworkResult.Loading -> {
                    _uiState.value = HomeUiState.Loading
                }
            }

            isLoadingMore = false
        }
    }

    fun retry() {
        currentOffset = 0
        _pokemonList.value = emptyList()
        loadPokemon()
    }
}

sealed class HomeUiState {
    object Loading : HomeUiState()
    object Success : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}