package com.example.navegacion.presentation.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.navegacion.presentation.components.NormalButton
import com.example.navegacion.presentation.components.CustomFloatingActionButton
import com.example.navegacion.presentation.components.CustomIconButton
import com.example.navegacion.presentation.components.CustomOutlinedButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text("Cuarto A")}
            )
        },
        floatingActionButton = {
            CustomFloatingActionButton()
        }
    ) {
        Content(it, navController)
    }
}

@Composable
fun Content(innerPaddingValues: PaddingValues, navController: NavController){
    Column(
        modifier = Modifier
            .padding(innerPaddingValues)
            .fillMaxWidth()
    ) {
        NormalButton("Click", onClick = {
            navController.navigate("Details")
        })
        NormalButton("Click 2", onClick = {
            navController.navigate("Details2")
        })
        NormalButton("Click 3", onClick = {
            navController.navigate("Details3")
        })
        CustomOutlinedButton()
        CustomIconButton()
    }
}