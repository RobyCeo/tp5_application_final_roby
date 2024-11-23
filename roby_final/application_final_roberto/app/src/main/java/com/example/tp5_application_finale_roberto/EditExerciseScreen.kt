package com.example.tp5_application_finale_roberto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditExerciseScreen(
    exercise: TrainingExercise, // Remplace Exercise par TrainingExercise
    onUpdate: (TrainingExercise) -> Unit,
    onBack: () -> Unit
) {
    val updatedSets = remember { mutableStateOf(exercise.sets.toString()) }
    val updatedReps = remember { mutableStateOf(exercise.repetitions.toString()) }
    val showBravo = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Modifier le plan : ${exercise.name}",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = updatedSets.value,
            onValueChange = { updatedSets.value = it },
            label = { Text("Nombre de sets") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        TextField(
            value = updatedReps.value,
            onValueChange = { updatedReps.value = it },
            label = { Text("Nombre de répétitions") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        if (showBravo.value) {
            Text(
                text = "Bravo!! 🎉 Vous avez progressé par rapport à la dernière session!",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Green,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }

        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { onBack() }) {
                Text("Annuler")
            }
            Button(onClick = {
                val newSets = updatedSets.value.toIntOrNull() ?: exercise.sets
                val newReps = updatedReps.value.toIntOrNull() ?: exercise.repetitions

                val isProgress = exercise.addProgression(newSets, newReps)
                if (isProgress) {
                    showBravo.value = true
                } else {
                    showBravo.value = false
                }

                onUpdate(exercise.copy(sets = newSets, repetitions = newReps)) // Met à jour TrainingExercise
                onBack()
            }) {
                Text("Enregistrer")
            }
        }
    }
}

