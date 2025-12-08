package com.example.examen3.ui.screens.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.examen3.ui.components.EmptyState
import com.example.examen3.ui.components.ErrorMessage
import com.example.examen3.ui.components.LoadingIndicator
import com.example.examen3.ui.components.PokemonCard
import com.example.examen3.ui.components.SearchBar
import com.example.examen3.viewmodels.SearchUiState
import com.example.examen3.viewmodels.SearchViewModel

@Composable
fun SearchScreen(
    onPokemonClick: (Int) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchBar(
            query = searchQuery,
            onQueryChange = { viewModel.onSearchQueryChange(it) },
            onSearch = { viewModel.searchPokemon(searchQuery) },
            onClear = { viewModel.clearSearch() },
            placeholder = "Buscar por nombre o ID..."
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.fillMaxSize()) {
            when (uiState) {
                is SearchUiState.Empty -> {
                    EmptyState(message = "Busca un Pokémon por nombre o ID")
                }
                is SearchUiState.Loading -> {
                    LoadingIndicator()
                }
                is SearchUiState.Success -> {
                    val pokemon = (uiState as SearchUiState.Success).pokemon
                    PokemonCard(
                        pokemon = pokemon,
                        onClick = { onPokemonClick(pokemon.id) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                is SearchUiState.Error -> {
                    ErrorMessage(
                        message = (uiState as SearchUiState.Error).message,
                        onRetry = null
                    )
                }
            }
        }
    }
}