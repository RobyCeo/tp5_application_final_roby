package com.example.tp5_application_finale_roberto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TrainingPlanScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Plan D'entrainement",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        // Exemple de contenu : une liste d'exercices avec répétitions
        val trainingPlan = listOf(
            "Pompes - 3 séries de 10 répétitions",
            "Squats - 3 séries de 15 répétitions",
            "Fentes - 3 séries de 12 répétitions par jambe"
        )
        trainingPlan.forEach { exercise ->
            Text(
                text = exercise,
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 8.dp),
                color = Color.Black
            )
        }
    }
}
