package com.example.navegacion.presentation.NavManager

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navegacion.ViewModel.Counter
import com.example.navegacion.presentation.views.HomeView
import com.example.navegacion.presentation.views.DetailsView
import com.example.navegacion.presentation.views.DetailsView2


@Composable
fun NavManager(counter: Counter){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "Home"
    ){
        composable("Home") {
            HomeView(navController)
        }
        composable("Details") {
            DetailsView(navController)
        }
        composable("Details2") {
            DetailsView2(navController, counter)
        }
    }
}