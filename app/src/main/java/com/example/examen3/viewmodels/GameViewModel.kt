package com.example.examen3.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examen3.domain.usacase.GetPokemonDetailUseCase
import com.example.examen3.utils.Constants
import com.example.examen3.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class GameViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<GameUiState>(GameUiState.Loading)
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private val _userAnswer = MutableStateFlow("")
    val userAnswer: StateFlow<String> = _userAnswer.asStateFlow()

    private val _streak = MutableStateFlow(0)
    val streak: StateFlow<Int> = _streak.asStateFlow()

    private val _showResult = MutableStateFlow<GameResult?>(null)
    val showResult: StateFlow<GameResult?> = _showResult.asStateFlow()

    private var currentPokemonName = ""

    init {
        loadRandomPokemon()
    }

    fun loadRandomPokemon() {
        viewModelScope.launch {
            _uiState.value = GameUiState.Loading
            _showResult.value = null
            _userAnswer.value = ""

            val randomId = Random.nextInt(1, Constants.MAX_POKEMON_ID + 1)

            when (val result = getPokemonDetailUseCase(randomId)) {
                is NetworkResult.Success -> {
                    result.data?.let { pokemon ->
                        currentPokemonName = pokemon.name
                        _uiState.value = GameUiState.Playing(
                            pokemonImage = pokemon.imageUrl,
                            pokemonId = pokemon.id
                        )
                    } ?: run {
                        _uiState.value = GameUiState.Error("No se pudo cargar el Pokémon")
                    }
                }
                is NetworkResult.Error -> {
                    _uiState.value = GameUiState.Error(result.message ?: "Error de conexión")
                }
                is NetworkResult.Loading -> {
                    _uiState.value = GameUiState.Loading
                }
            }
        }
    }

    fun onAnswerChange(answer: String) {
        _userAnswer.value = answer
    }

    fun checkAnswer() {
        val userAnswerNormalized = _userAnswer.value.trim().lowercase()
        val isCorrect = userAnswerNormalized == currentPokemonName.lowercase()

        if (isCorrect) {
            _streak.value += 1
            _showResult.value = GameResult.Correct(currentPokemonName)
        } else {
            _showResult.value = GameResult.Wrong(currentPokemonName)
            _streak.value = 0
        }
    }

    fun nextPokemon() {
        loadRandomPokemon()
    }

    fun resetGame() {
        _streak.value = 0
        loadRandomPokemon()
    }
}

sealed class GameUiState {
    object Loading : GameUiState()
    data class Playing(val pokemonImage: String, val pokemonId: Int) : GameUiState()
    data class Error(val message: String) : GameUiState()
}

sealed class GameResult {
    data class Correct(val pokemonName: String) : GameResult()
    data class Wrong(val pokemonName: String) : GameResult()
}