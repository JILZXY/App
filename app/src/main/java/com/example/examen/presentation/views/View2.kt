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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.examen.data.Entity.User
import com.example.examen.viewModel.UserViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun View2(navController: NavController, viewModel: UserViewModel) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {Text("Vista 2")},
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
        ContenteView2(it, viewModel)
    }
}

@Composable
fun ContenteView2(innerPadding: PaddingValues, viewModel: UserViewModel){
    val scope = rememberCoroutineScope()
    var texto1 by rememberSaveable { mutableStateOf("") }
    var texto2 by rememberSaveable { mutableStateOf("") }
    var texto3 by rememberSaveable { mutableStateOf("") }
    var texto4 by rememberSaveable { mutableStateOf("") }
    val usuarioRecuperado by viewModel.user.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .background(Color.Transparent),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF003138)),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                ){
                Spacer(modifier = Modifier.height(12.dp))
                Text("Sube tu informacion")
                TextField(
                    value = texto1,
                    onValueChange = { text -> texto1 = text },
                    textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                    label = { Text("Nombres") },
                )
                TextField(
                    value = texto2,
                    onValueChange = { text -> texto2 = text },
                    label = { Text("Apellidos") }
                )
                TextField(
                    value = texto3,
                    onValueChange = { text -> texto3 = text },
                    label = { Text("Identificate pa (numero ID)") }
                )
                Spacer(modifier = Modifier.height(30.dp))
                Button(onClick = {
                    scope.launch {
                        val userId = texto3.toIntOrNull()
                        if (userId != null && userId > 0) {
                            val user = User(uid = userId, firstName = texto1, lastName = texto2)
                            viewModel.insertUser(user)
                        }
                    }

                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00796B),
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(6.dp),
                ) {
                    Text("Guardar")
                }
            }
        }

        Card(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .background(Color.Transparent),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF003138)),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                TextField(
                    value = texto4,
                    onValueChange = { text -> texto4 = text },
                    label = { Text("Buscale por ID papi") }
                )
                Spacer(modifier = Modifier.padding(vertical = 12.dp))
                Button(onClick = {
                    val userId = texto4.toIntOrNull() ?: -1
                    viewModel.searchUserById(userId)
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00796B),
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(6.dp),) { Text("Buscar") }
                Spacer(modifier = Modifier.height(20.dp))
                usuarioRecuperado?.let {
                    Text("Usuario guardado:")
                    Text("Nombre: ${it.firstName}")
                    Text("Apellido: ${it.lastName}")
                    Text("ID: ${it.uid}")
                }
            }
        }
    }
}