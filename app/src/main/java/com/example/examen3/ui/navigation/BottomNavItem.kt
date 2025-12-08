package com.example.examen3.ui.navigation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.examen3.utils.Constants

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem(
        route = Constants.Routes.HOME,
        title = "Inicio",
        icon = Icons.Default.Home
    )

    object Search : BottomNavItem(
        route = Constants.Routes.SEARCH,
        title = "Buscar",
        icon = Icons.Default.Search
    )

    object Game : BottomNavItem(
        route = "game",
        title = "Juego",
        icon = Icons.Default.SportsEsports
    )
    object Favorites : BottomNavItem(
        route = Constants.Routes.FAVORITES,
        title = "Favoritos",
        icon = Icons.Default.Favorite
    )
}

fun getBottomNavItems() = listOf(
    BottomNavItem.Home,
    BottomNavItem.Search,
    BottomNavItem.Game,
    BottomNavItem.Favorites
)