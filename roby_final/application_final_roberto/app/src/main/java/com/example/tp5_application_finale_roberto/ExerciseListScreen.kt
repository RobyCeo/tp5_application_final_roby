package com.example.tp5_application_finale_roberto

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ExerciseListScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Liste des exercices",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        val exercises = listOf("Pompes", "Squats", "Fentes", "Planche", "Tractions")
        exercises.forEach { exercise ->
            Text(
                text = exercise,
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}
