package com.example.practica1u3.Presentation.Views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
//import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.practica1u3.Data.Entity.Student
import com.example.practica1u3.Data.viewModels.studentViewModel
import com.example.practica1u3.Presentation.Components.BottomAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStudent(navController: NavController, viewModel: studentViewModel) {
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var grade by remember { mutableStateOf("") }
    var group by remember { mutableStateOf("") }
    var score by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf(false) }
    var lastNameError by remember { mutableStateOf(false) }
    var gradeError by remember { mutableStateOf(false) }
    var groupError by remember { mutableStateOf(false) }
    var scoreError by remember { mutableStateOf(false) }

    var showSuccessDialog by remember { mutableStateOf(false) }
    var savedStudentName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agregar Estudiante") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            BottomAppBar(navController = navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Tarjeta de información
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Column {
                        Text(
                            text = "Nuevo Estudiante",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "Complete todos los campos",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Sección de datos personales
            Text(
                text = "👤 Datos Personales",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    nameError = false
                },
                label = { Text("Nombre") },
                placeholder = { Text("Ej: Juan") },
                modifier = Modifier.fillMaxWidth(),
                isError = nameError,
                supportingText = {
                    if (nameError) {
                        Text("El nombre es requerido")
                    }
                },
                singleLine = true
            )

            OutlinedTextField(
                value = lastName,
                onValueChange = {
                    lastName = it
                    lastNameError = false
                },
                label = { Text("Apellido") },
                placeholder = { Text("Ej: Pérez") },
                modifier = Modifier.fillMaxWidth(),
                isError = lastNameError,
                supportingText = {
                    if (lastNameError) {
                        Text("El apellido es requerido")
                    }
                },
                singleLine = true
            )

            Divider()

            // Sección académica
            Text(
                text = "📚 Información Académica",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = grade,
                    onValueChange = {
                        if (it.length <= 2) {
                            grade = it
                            gradeError = false
                        }
                    },
                    label = { Text("Grado") },
                    placeholder = { Text("Ej: 1") },
                    modifier = Modifier.weight(1f),
                    isError = gradeError,
                    supportingText = {
                        if (gradeError) {
                            Text("Requerido")
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                OutlinedTextField(
                    value = group,
                    onValueChange = {
                        if (it.length <= 2) {
                            group = it.uppercase()
                            groupError = false
                        }
                    },
                    label = { Text("Grupo") },
                    placeholder = { Text("Ej: A") },
                    modifier = Modifier.weight(1f),
                    isError = groupError,
                    supportingText = {
                        if (groupError) {
                            Text("Requerido")
                        }
                    },
                    singleLine = true
                )
            }

            OutlinedTextField(
                value = score,
                onValueChange = {
                    score = it
                    scoreError = false
                },
                label = { Text("Calificación") },
                placeholder = { Text("Ej: 95.5") },
                modifier = Modifier.fillMaxWidth(),
                isError = scoreError,
                supportingText = {
                    if (scoreError) {
                        Text("Ingrese una calificación válida (0-100)")
                    } else {
                        Text("Calificación de 0 a 100")
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                trailingIcon = {
                    Icon(Icons.Default.AddCircle, contentDescription = null)
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Botones
            Button(
                onClick = {
                    // Validaciones
                    nameError = name.isBlank()
                    lastNameError = lastName.isBlank()
                    gradeError = grade.isBlank()
                    groupError = group.isBlank()

                    val scoreValue = score.toFloatOrNull()
                    scoreError = scoreValue == null || scoreValue < 0 || scoreValue > 100

                    // Si no hay errores, guardar usando insertStudents (vararg)
                    if (!nameError && !lastNameError && !gradeError && !groupError && !scoreError) {
                        val newStudent = Student(
                            id = 0,
                            name = name.trim(),
                            lastName = lastName.trim(),
                            grade = grade.trim(),
                            group = group.trim(),
                            score = scoreValue!!
                        )

                        // Usar insertStudents con vararg
                        viewModel.insertStudents(newStudent)
                        savedStudentName = "${name.trim()} ${lastName.trim()}"
                        showSuccessDialog = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Guardar Estudiante", style = MaterialTheme.typography.titleMedium)
            }

            OutlinedButton(
                onClick = {
                    name = ""
                    lastName = ""
                    grade = ""
                    group = ""
                    score = ""
                    nameError = false
                    lastNameError = false
                    gradeError = false
                    groupError = false
                    scoreError = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Limpiar Formulario")
            }
        }
    }

    // Diálogo de éxito
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { },
            icon = {
                Text("✅", style = MaterialTheme.typography.displayMedium)
            },
            title = {
                Text("¡Estudiante Registrado!")
            },
            text = {
                Text("$savedStudentName ha sido agregado exitosamente.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        // Limpiar formulario
                        name = ""
                        lastName = ""
                        grade = ""
                        group = ""
                        score = ""
                    }
                ) {
                    Text("Agregar Otro")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showSuccessDialog = false
                        navController.navigate("Tab1") {
                            popUpTo("Tab1") { inclusive = true }
                        }
                    }
                ) {
                    Text("Ver Lista")
                }
            }
        )
    }
}