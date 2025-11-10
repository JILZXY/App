package com.example.examen.presentation.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.examen.data.DataStoreDarkMode
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun View1(navController: NavController, darkModeStore: DataStoreDarkMode, darkMode: Boolean) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {Text("Vista 1")},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "")
                    }
                }
            )
        }
    ) {
        ContenteView(it, darkModeStore, darkMode)
    }
}

@Composable
fun ContenteView(innerPadding: PaddingValues, darkModeStore: DataStoreDarkMode, darkMode: Boolean){
    val scope = rememberCoroutineScope()
    Column (verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(innerPadding).fillMaxSize()){
        Card(
            modifier = Modifier
                .background(Color.Transparent),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF003138)),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(20.dp)) {
                Text("Cambiale de tema pa")
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    scope.launch {
                        if(darkMode) darkModeStore.saveDarkMode(false)
                        else darkModeStore.saveDarkMode(true)
                    }
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00796B),
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(6.dp),
                    ) { Text("A ver calale")}
            }
        }
    }
}