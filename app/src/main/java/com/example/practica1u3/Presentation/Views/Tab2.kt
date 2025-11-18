package com.example.practica1u3.Presentation.Views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
//import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun Tab2(navController: NavController, viewModel: studentViewModel) {
    val students by viewModel.allStudents.collectAsState()
    val averageScore by viewModel.averageScore.collectAsState()
    val minScore by viewModel.minScore.collectAsState()
    val top3Students by viewModel.top3Students.collectAsState()

    var selectedGroup by remember { mutableStateOf("") }
    var showGroupDialog by remember { mutableStateOf(false) }

    // Obtener lista de grupos únicos
    val availableGroups = remember(students) {
        students.map { it.group }.distinct().sorted()
    }

    // Cargar estadísticas cuando se abre la pantalla
    LaunchedEffect(Unit) {
        viewModel.fetchAverageScore()
        viewModel.fetchMinScore()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Estadísticas") },
                actions = {
                    IconButton(onClick = {
                        viewModel.fetchAverageScore()
                        viewModel.fetchMinScore()
                        if (selectedGroup.isNotEmpty()) {
                            viewModel.fetchTop3Students(selectedGroup)
                        }
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Actualizar")
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
                        text = "📊",
                        style = MaterialTheme.typography.displayLarge
                    )
                    Text(
                        text = "No hay estadísticas disponibles",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Agrega estudiantes para ver sus estadísticas",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Tarjeta de resumen
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "📈 Resumen General",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Divider(modifier = Modifier.padding(vertical = 4.dp))
                        Text(
                            text = "Total de estudiantes: ${students.size}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Grupos disponibles: ${availableGroups.joinToString(", ")}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                // Estadísticas principales
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        title = "Promedio",
                        value = averageScore?.let { String.format("%.2f", it) } ?: "---",
                        emoji = "📊",
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.secondaryContainer
                    )

                    StatCard(
                        title = "Mínima",
                        value = minScore?.let { String.format("%.2f", it) } ?: "---",
                        emoji = "📉",
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.tertiaryContainer
                    )
                }

                // Calificación máxima
                val maxScore = students.maxOfOrNull { it.score } ?: 0f
                StatCard(
                    title = "Calificación Máxima",
                    value = String.format("%.2f", maxScore),
                    emoji = "📈",
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primaryContainer
                )

                // Selector de grupo para Top 3
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Selecciona un grupo para ver el Top 3:",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Button(
                            onClick = { showGroupDialog = true },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = if (selectedGroup.isEmpty())
                                    "Seleccionar Grupo"
                                else
                                    "Grupo: $selectedGroup"
                            )
                        }
                    }
                }

                // Top 3 estudiantes
                if (selectedGroup.isNotEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AccountBox,
                                    contentDescription = "Trofeo",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "Top 3 Mejores Estudiantes - Grupo $selectedGroup",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Divider()

                            if (top3Students.isEmpty()) {
                                Text(
                                    text = "No hay estudiantes en este grupo",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            } else {
                                top3Students.forEachIndexed { index, student ->
                                    TopStudentItem(
                                        position = index + 1,
                                        student = student
                                    )
                                    if (index < top3Students.size - 1) {
                                        Divider(modifier = Modifier.padding(vertical = 4.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Diálogo para seleccionar grupo
    if (showGroupDialog) {
        AlertDialog(
            onDismissRequest = { showGroupDialog = false },
            title = { Text("Seleccionar Grupo") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    availableGroups.forEach { group ->
                        Button(
                            onClick = {
                                selectedGroup = group
                                viewModel.fetchTop3Students(group)
                                showGroupDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Grupo $group")
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showGroupDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    emoji: String,
    modifier: Modifier = Modifier,
    color: androidx.compose.ui.graphics.Color
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = emoji,
                style = MaterialTheme.typography.displaySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun TopStudentItem(position: Int, student: Student) {
    val medal = when (position) {
        1 -> "🥇"
        2 -> "🥈"
        3 -> "🥉"
        else -> "🏅"
    }

    val containerColor = when (position) {
        1 -> MaterialTheme.colorScheme.primaryContainer
        2 -> MaterialTheme.colorScheme.secondaryContainer
        3 -> MaterialTheme.colorScheme.tertiaryContainer
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = containerColor,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = medal,
                    style = MaterialTheme.typography.headlineMedium
                )
                Column {
                    Text(
                        text = "${student.name} ${student.lastName}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${student.grade}° ${student.group}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Surface(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = "${student.score}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}