package com.example.navegacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.navegacion.ViewModel.Counter
import com.example.navegacion.presentation.NavManager.NavManager
import com.example.navegacion.ui.theme.NavegacionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val counterViewModel: Counter by viewModels()
        enableEdgeToEdge()
        setContent {
            NavegacionTheme {
                NavManager(counterViewModel)
            }
        }
    }
}