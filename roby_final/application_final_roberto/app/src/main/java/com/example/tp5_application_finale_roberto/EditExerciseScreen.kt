package com.example.tp5_application_finale_roberto

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun EditExerciseScreen(
    context: Context, // Ajout du contexte pour utiliser MediaPlayer
    exercise: TrainingExercise,
    onUpdate: (TrainingExercise) -> Unit,
    onBack: () -> Unit
) {
    val updatedSets = remember { mutableStateOf(exercise.sets.toString()) }
    val updatedReps = remember { mutableStateOf(exercise.repetitions.toString()) }
    val updatedWeight = remember { mutableStateOf(exercise.weight.toString()) }
    val showMessage = remember { mutableStateOf(false) }
    val messageColor = remember { mutableStateOf(Color.Green) }
    val messageText = remember { mutableStateOf("") }

    // Animation state
    val scaleAnimation = remember { Animatable(1f) }

    // Animation logic with sound
    LaunchedEffect(showMessage.value) {
        if (showMessage.value) {
            // Play sound during animation
            if (messageColor.value == Color.Green) {
                playSound(context, R.raw.progression) // Son de progression
            } else {
                playSound(context, R.raw.regression) // Son de régression
            }

            scaleAnimation.animateTo(
                targetValue = 1.2f,
                animationSpec = tween(durationMillis = 300, easing = LinearEasing)
            )
            scaleAnimation.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 300, easing = LinearEasing)
            )
            showMessage.value = false
        }
    }

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

        // Display the animated message
        if (showMessage.value) {
            Text(
                text = messageText.value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = messageColor.value,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .graphicsLayer(
                        scaleX = scaleAnimation.value,
                        scaleY = scaleAnimation.value
                    )
            )
        }

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

        TextField(
            value = updatedWeight.value,
            onValueChange = { updatedWeight.value = it },
            label = { Text("Poids (kg)") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { onBack() }) {
                Text("Retour")
            }
            Button(onClick = {
                // Validation des entrées
                if (updatedSets.value.isBlank() ||
                    updatedReps.value.isBlank() ||
                    updatedWeight.value.isBlank()
                ) {
                    messageText.value = "Veuillez remplir tous les champs."
                    messageColor.value = Color.Red
                    showMessage.value = true
                    return@Button
                }

                val newSets = updatedSets.value.toIntOrNull()
                val newReps = updatedReps.value.toIntOrNull()
                val newWeight = updatedWeight.value.toIntOrNull()

                if (newSets == null || newReps == null || newWeight == null) {
                    messageText.value = "Veuillez entrer des valeurs numériques valides."
                    messageColor.value = Color.Red
                    showMessage.value = true
                    return@Button
                }

                val isProgress = exercise.addProgression(newSets, newReps, newWeight)

                if (isProgress) {
                    messageText.value = "Bravo!! 🎉 Vous avez progressé!"
                    messageColor.value = Color.Green
                } else {
                    messageText.value = "Attention : Régression ou aucune progression."
                    messageColor.value = Color.Red
                }

                showMessage.value = true
                onUpdate(exercise.copy(sets = newSets, repetitions = newReps, weight = newWeight))
            }) {
                Text("Enregistrer")
            }
        }
    }
}

// Fonction utilitaire pour jouer un son
private suspend fun playSound(context: Context, soundResId: Int) {
    withContext(Dispatchers.IO) {
        val mediaPlayer = MediaPlayer.create(context, soundResId)
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            it.release()
        }
    }
}
