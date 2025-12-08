package com.example.examen3.ui.screens.game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.examen3.ui.components.ErrorMessage
import com.example.examen3.ui.components.LoadingIndicator
import com.example.examen3.ui.theme.PokemonYellow
import com.example.examen3.ui.theme.SuccessColor
import com.example.examen3.utils.capitalizeFirst
import com.example.examen3.viewmodels.GameResult
import com.example.examen3.viewmodels.GameUiState
import com.example.examen3.viewmodels.GameViewModel

@Composable
fun GameScreen(
    viewModel: GameViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val userAnswer by viewModel.userAnswer.collectAsState()
    val streak by viewModel.streak.collectAsState()
    val showResult by viewModel.showResult.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is GameUiState.Loading -> LoadingIndicator()
            is GameUiState.Error -> {
                ErrorMessage(
                    message = (uiState as GameUiState.Error).message,
                    onRetry = { viewModel.loadRandomPokemon() }
                )
            }
            is GameUiState.Playing -> {
                val playingState = uiState as GameUiState.Playing

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = PokemonYellow
                            )
                        ) {
                            Text(
                                text = "Racha: $streak",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                color = Color.Black
                            )
                        }

                        IconButton(onClick = { viewModel.resetGame() }) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Reiniciar",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "¿Quién es ese Pokémon?",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Card(
                        modifier = Modifier
                            .size(280.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surface),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = playingState.pokemonImage,
                                contentDescription = "Silueta de Pokémon",
                                modifier = Modifier.size(240.dp),
                                contentScale = ContentScale.Fit,
                                colorFilter = if (showResult == null) {
                                    ColorFilter.colorMatrix(
                                        ColorMatrix().apply {
                                            setToScale(0f, 0f, 0f, 1f)
                                        }
                                    )
                                } else null
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    AnimatedVisibility(
                        visible = showResult != null,
                        enter = fadeIn() + scaleIn(),
                        exit = fadeOut() + scaleOut()
                    ) {
                        showResult?.let { result ->
                            ResultCard(
                                result = result,
                                onNext = { viewModel.nextPokemon() }
                            )
                        }
                    }

                    AnimatedVisibility(visible = showResult == null) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            OutlinedTextField(
                                value = userAnswer,
                                onValueChange = { viewModel.onAnswerChange(it) },
                                label = { Text("Tu respuesta") },
                                placeholder = { Text("Escribe el nombre del Pokémon") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = { viewModel.checkAnswer() }
                                )
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = { viewModel.checkAnswer() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                enabled = userAnswer.isNotBlank()
                            ) {
                                Text(
                                    text = "Contestar",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ResultCard(
    result: GameResult,
    onNext: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when (result) {
                is GameResult.Correct -> SuccessColor
                is GameResult.Wrong -> MaterialTheme.colorScheme.error
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = when (result) {
                    is GameResult.Correct -> "¡Correcto! 🎉"
                    is GameResult.Wrong -> "Incorrecto 😔"
                },
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = when (result) {
                    is GameResult.Correct -> "Era ${result.pokemonName.capitalizeFirst()}"
                    is GameResult.Wrong -> "Era ${result.pokemonName.capitalizeFirst()}"
                },
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onNext,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Siguiente Pokémon")
            }
        }
    }
}