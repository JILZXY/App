package com.example.practica1u3.Presentation.Views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.practica1u3.Data.viewModels.studentViewModel
import com.example.practica1u3.Presentation.Components.BottomAppBar

@Composable
fun AddStudent(navController: NavController, studentViewModel: studentViewModel) {
    Scaffold (
        bottomBar = {
            BottomAppBar(navController = navController)
        }
    ) {
        Content(it)
    }
}

@Composable
fun Content(innerPadding: PaddingValues) {
    Column(modifier = Modifier.padding(innerPadding)) {
        Text("HOLA")
    }
}