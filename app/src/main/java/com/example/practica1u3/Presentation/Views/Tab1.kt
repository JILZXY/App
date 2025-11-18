package com.example.practica1u3.Presentation.Views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.practica1u3.Data.Entity.Student
import com.example.practica1u3.Data.viewModels.studentViewModel
import com.example.practica1u3.Presentation.Components.BottomAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tab1(navController: NavController, viewModel: studentViewModel) {
    val students by viewModel.allStudents.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Estudiantes") },
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
        if (students.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "📚",
                        style = MaterialTheme.typography.displayLarge
                    )
                    Text(
                        text = "No hay estudiantes registrados",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Presiona el botón + para agregar",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Total de estudiantes:",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "${students.size}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                items(students) { student ->
                    StudentCard(
                        student = student,
                        onDelete = { viewModel.deleteStudent(student) }
                    )
                }
            }
        }
    }
}

@Composable
fun StudentCard(
    student: Student,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${student.name} ${student.lastName}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Grado: ${student.grade}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Grupo: ${student.group}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    color = when {
                        student.score >= 90 -> MaterialTheme.colorScheme.primaryContainer
                        student.score >= 70 -> MaterialTheme.colorScheme.secondaryContainer
                        else -> MaterialTheme.colorScheme.errorContainer
                    },
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = "Calificación: ${student.score}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = when {
                            student.score >= 90 -> MaterialTheme.colorScheme.onPrimaryContainer
                            student.score >= 70 -> MaterialTheme.colorScheme.onSecondaryContainer
                            else -> MaterialTheme.colorScheme.onErrorContainer
                        },
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            IconButton(
                onClick = onDelete,
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar estudiante"
                )
            }
        }
    }
}