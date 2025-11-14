package com.example.practica1u3.Presentation.NavManager

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.practica1u3.Data.viewModels.studentViewModel
import com.example.practica1u3.Presentation.Views.AddStudent
import com.example.practica1u3.Presentation.Views.Tab1
import com.example.practica1u3.Presentation.Views.Tab2

@Composable
fun NavManager(studentViewModelq: studentViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "AddStudent") {
        composable("Tab1") { Tab1(navController,studentViewModelq) }
        composable("Tab2") { Tab2(navController,studentViewModelq) }
        composable("AddStudent") { AddStudent(navController,studentViewModelq) }
    }
}