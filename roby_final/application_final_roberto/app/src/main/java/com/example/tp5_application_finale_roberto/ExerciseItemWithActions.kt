package com.example.tp5_application_finale_roberto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ExerciseItemWithActions(
    exercise: TrainingExercise,
    onDelete: () -> Unit,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "${exercise.name} (${exercise.sets} sets, ${exercise.repetitions} répétitions)",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
            Text(
                text = "Durée : ${exercise.planDuration} semaines",
                fontSize = 16.sp,
                color = Color.Black
            )
            Text(
                text = "Progression :",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue,
                modifier = Modifier.padding(top = 8.dp)
            )
            exercise.getProgression().forEach { (week, progression) ->
                Text(
                    text = "Semaine $week : ${progression.first} sets, ${progression.second} répétitions, ${progression.third} kg",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
        Row {
            Button(
                onClick = { onSelect() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("Choisir", color = Color.White)
            }
            Button(
                onClick = { onDelete() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Supprimer", color = Color.White)
            }
        }
    }
}

