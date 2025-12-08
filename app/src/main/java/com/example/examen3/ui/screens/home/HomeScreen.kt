package com.example.examen3.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.examen3.ui.components.ErrorMessage
import com.example.examen3.ui.components.LoadingIndicator
import com.example.examen3.ui.components.PokemonCard
import com.example.examen3.viewmodels.HomeUiState
import com.example.examen3.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    onPokemonClick: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val pokemonList by viewModel.pokemonList.collectAsState()
    val listState = rememberLazyListState()

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem != null && lastVisibleItem.index >= pokemonList.size - 5
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value && uiState is HomeUiState.Success) {
            viewModel.loadPokemon()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is HomeUiState.Loading -> {
                if (pokemonList.isEmpty()) {
                    LoadingIndicator()
                } else {
                    LazyColumn(
                        state = listState,
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(pokemonList) { pokemon ->
                            PokemonCard(
                                pokemon = pokemon,
                                onClick = { onPokemonClick(pokemon.id) }
                            )
                        }
                    }
                }
            }
            is HomeUiState.Success -> {
                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(pokemonList) { pokemon ->
                        PokemonCard(
                            pokemon = pokemon,
                            onClick = { onPokemonClick(pokemon.id) }
                        )
                    }
                }
            }
            is HomeUiState.Error -> {
                ErrorMessage(
                    message = (uiState as HomeUiState.Error).message,
                    onRetry = { viewModel.retry() }
                )
            }
        }
    }
}