package com.example.practica1u3.Presentation.Components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

@Composable
fun BottomAppBar(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("AddStudent"){
                    popUpTo(navController.graph.findStartDestination().id){
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Home"
                )
            },
            label = {Text("AddStudent")}
        )
        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("Tab1"){
                    popUpTo(navController.graph.findStartDestination().id){
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Tab1"
                )
            },
            label = {Text("Tab1")}
        )
        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("Tab1"){
                    popUpTo(navController.graph.findStartDestination().id){
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Tab1"
                )
            },
            label = {Text("Tab1")}
        )

    }
}
