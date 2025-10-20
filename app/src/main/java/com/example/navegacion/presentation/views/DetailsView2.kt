package com.example.navegacion.presentation.views

import android.telecom.Call
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.navegacion.ViewModel.Counter
import com.example.navegacion.data.StudentsObject
import com.example.navegacion.presentation.components.NormalButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsView2(navController: NavController, counter: Counter){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {Text("Details B")},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ){
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "")
                    }
                }
            )
        }
    ) {
        DetailsContent2(it, counter)
    }
}

@Composable
fun DetailsContent2(innerPaddingValues: PaddingValues, counter: Counter){
    Column(
        modifier = Modifier
            .padding(innerPaddingValues)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {counter.add()}) {
            Text("Clickeame para sumar bro")
        }

        Button(onClick = {counter.less()}) {
            Text("Clickeame para restar bro")
        }

        Button(onClick = {counter.mul()}) {
            Text("Clickeame para elevarme al cuadrado bro")
        }
        Button(onClick = {counter.division()}) {
            Text("Clickeame para dividirme bro")
        }

        Text("${counter.counter.value}")


    }
}