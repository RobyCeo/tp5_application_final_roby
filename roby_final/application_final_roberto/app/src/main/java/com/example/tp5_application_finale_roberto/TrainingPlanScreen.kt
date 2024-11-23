package com.example.tp5_application_finale_roberto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TrainingPlanScreen() {
    // Liste des exercices
    val exercises = remember { mutableStateListOf<Exercise>() }
    val exerciseName = remember { mutableStateOf("") }
    val sets = remember { mutableStateOf("") }

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

        // Formulaire pour ajouter un nouvel exercice
        TextField(
            value = exerciseName.value,
            onValueChange = { exerciseName.value = it },
            label = { Text("Nom de l'exercice") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        TextField(
            value = sets.value,
            onValueChange = { sets.value = it },
            label = { Text("Nombre de sets") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )
        Button(
            onClick = {
                if (exerciseName.value.isNotBlank() && sets.value.isNotBlank()) {
                    val newExercise = Exercise(exerciseName.value, sets.value.toInt())
                    exercises.add(newExercise)
                    exerciseName.value = "" // Réinitialise le formulaire
                    sets.value = ""
                }
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Ajouter l'exercice")
        }

        // Afficher la liste des exercices et leur progression
        if (exercises.isNotEmpty()) {
            Text(
                text = "Votre plan d'entraînement :",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
            exercises.forEach { exercise ->
                ExerciseItem(exercise)
            }
        }
    }
}

// Composable pour afficher un exercice et sa progression hebdomadaire
@Composable
fun ExerciseItem(exercise: Exercise) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(
            text = "${exercise.name} (${exercise.sets} sets)",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
        Text(
            text = "Progression hebdomadaire :",
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
        )
        exercise.getProgression().forEach { (week, sets) -> // Fix: Destructure keys and values
            Text(
                text = "Semaine $week : $sets sets",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}


// Modèle de données pour un exercice
data class Exercise(val name: String, val sets: Int) {
    fun getProgression(): Map<Int, Int> {
        // Exemple de progression : augmente de 1 set par semaine
        return (1..4).associateWith { week -> sets + week - 1 }
    }
}
