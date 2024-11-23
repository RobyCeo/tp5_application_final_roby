package com.example.tp5_application_finale_roberto
import androidx.compose.foundation.lazy.items // This is crucial for items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
    // List of exercises
    val exercises = remember { mutableStateListOf<Exercise>() }
    val exerciseName = remember { mutableStateOf("") }
    val sets = remember { mutableStateOf("") }
    val repetitions = remember { mutableStateOf("") }
    val planDuration = remember { mutableStateOf("") }
    // test pas rapport
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Plan D'entrainement",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Form inputs for adding a new exercise
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
        TextField(
            value = repetitions.value,
            onValueChange = { repetitions.value = it },
            label = { Text("Nombre de répétitions") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )
        TextField(
            value = planDuration.value,
            onValueChange = { planDuration.value = it },
            label = { Text("Durée du plan (en semaines)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )
        Button(
            onClick = {
                if (
                    exerciseName.value.isNotBlank() &&
                    sets.value.isNotBlank() &&
                    repetitions.value.isNotBlank() &&
                    planDuration.value.isNotBlank()
                ) {
                    val newExercise = Exercise(
                        name = exerciseName.value,
                        sets = sets.value.toInt(),
                        repetitions = repetitions.value.toInt(),
                        planDuration = planDuration.value.toInt()
                    )
                    exercises.add(newExercise)
                    exerciseName.value = "" // Reset the form
                    sets.value = ""
                    repetitions.value = ""
                    planDuration.value = ""
                }
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Ajouter l'exercice")
        }

        // Display the list of exercises in a LazyColumn
        if (exercises.isNotEmpty()) {
            Text(
                text = "Votre plan d'entraînement :",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(exercises) { exercise ->
                    ExerciseItem(exercise)
                }
            }
        }
    }
}

// Composable to display an exercise and its progression
@Composable
fun ExerciseItem(exercise: Exercise) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(
            text = "${exercise.name} (${exercise.sets} sets, ${exercise.repetitions} répétitions)",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
        Text(
            text = "Progression sur ${exercise.planDuration} semaines :",
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
        )
        exercise.getProgression().forEach { (week, progression) ->
            Text(
                text = "Semaine $week : ${progression.first} sets, ${progression.second} répétitions",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

// Data model for an exercise
data class Exercise(
    val name: String,
    val sets: Int,
    val repetitions: Int,
    val planDuration: Int
) {
    fun getProgression(): Map<Int, Pair<Int, Int>> {
        // Generate progression over the weeks
        return (1..planDuration).associateWith { week ->
            // Increase sets and repetitions progressively
            val updatedSets = sets + (week - 1) // Add 1 set per week
            val updatedReps = repetitions + (week - 1) * 2 // Add 2 reps per week
            updatedSets to updatedReps
        }
    }
}

