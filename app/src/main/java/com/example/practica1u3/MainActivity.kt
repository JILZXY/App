package com.example.practica1u3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.practica1u3.Data.InterfaceStudent.StudentDAO
import com.example.practica1u3.Data.viewModels.StudentFactory
import com.example.practica1u3.Data.viewModels.studentViewModel
import com.example.practica1u3.Presentation.NavManager.NavManager
import com.example.practica1u3.ui.theme.Practica1U3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModelStudent = studentViewModel by viewModels {
                StudentFactory(StudentDAO)
            }
            Practica1U3Theme {
                NavManager(viewModelStudent)
            }
        }
    }
}