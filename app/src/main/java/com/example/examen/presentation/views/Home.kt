package com.example.examen.presentation.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Vista super principal rey") }
            )
        }
    ) {
        ContentMain(it, navController)
    }
}

@Composable
fun ContentMain(innerPadding: PaddingValues, navController: NavController) {
    Column(modifier = Modifier.padding(innerPadding).fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Text("Elige la vista que gustes rey")
        Spacer(modifier = Modifier.height(30.dp))
        Button( onClick = {
            navController.navigate("View1")
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00796B),
                contentColor = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(6.dp),) {
            Text("Vista one")
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button( onClick = {
            navController.navigate("View2")
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00796B),
                contentColor = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(6.dp),) {
            Text("Vista two")
        }
    }
}