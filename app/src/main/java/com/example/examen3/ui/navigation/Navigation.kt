package com.example.examen3.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.examen3.ui.screens.detail.DetailScreen
import com.example.examen3.ui.screens.favorites.FavoritesScreen
import com.example.examen3.ui.screens.game.GameScreen
import com.example.examen3.ui.screens.home.HomeScreen
import com.example.examen3.ui.screens.search.SearchScreen
import com.example.examen3.utils.Constants

@Composable
fun PokedexNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomNavItems = getBottomNavItems()
    val showBottomBar = currentDestination?.route in bottomNavItems.map { it.route }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        val selected = currentDestination?.hierarchy?.any {
                            it.route == item.route
                        } == true

                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title
                                )
                            },
                            label = {
                                Text(text = item.title)
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Constants.Routes.HOME,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Constants.Routes.HOME) {
                HomeScreen(
                    onPokemonClick = { pokemonId ->
                        navController.navigate("${Constants.Routes.DETAIL}/$pokemonId")
                    }
                )
            }

            composable(Constants.Routes.SEARCH) {
                SearchScreen(
                    onPokemonClick = { pokemonId ->
                        navController.navigate("${Constants.Routes.DETAIL}/$pokemonId")
                    }
                )
            }

            composable(Constants.Routes.FAVORITES) {
                FavoritesScreen(
                    onPokemonClick = { pokemonId ->
                        navController.navigate("${Constants.Routes.DETAIL}/$pokemonId")
                    }
                )
            }

            composable("game") {
                GameScreen()
            }

            composable(
                route = "${Constants.Routes.DETAIL}/{${Constants.Args.POKEMON_ID}}",
                arguments = listOf(
                    navArgument(Constants.Args.POKEMON_ID) {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val pokemonId = backStackEntry.arguments?.getInt(Constants.Args.POKEMON_ID) ?: 1
                DetailScreen(
                    pokemonId = pokemonId,
                    onBackClick = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}