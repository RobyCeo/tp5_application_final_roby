package com.example.tp5_application_finale_roberto

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ExerciseListScreen(context: Context) {
    val exercises = mapOf(
        "Pompes" to "Exercice pour renforcer les pectoraux et les triceps.",
        "Squats" to "Exercice pour renforcer les quadriceps et les fessiers.",
        "Fentes" to "Exercice pour travailler les jambes et l'équilibre.",
        "Planche" to "Exercice isométrique pour renforcer le tronc.",
        "Tractions" to "Exercice pour renforcer le dos et les biceps.",
        "Développé Couché" to "Exercice pour les pectoraux et les triceps avec une barre.",
        "Soulevé de terre" to "Exercice complet pour travailler le bas du dos et les jambes.",
        "Flexion des biceps avec la barre" to "Exercice pour cibler les biceps."
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Text(
            text = "Liste des exercices avec videos et descriptions",
            fontSize = 30.sp,
            modifier = Modifier.padding(bottom = 16.dp),
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.Serif
        )
        exercises.forEach { (exercise, description) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = exercise,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = description,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_video),
                    contentDescription = "Voir la vidéo",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            openVideo(context, exercise)
                        }
                        .padding(start = 8.dp)
                )
            }
        }
    }
}

fun openVideo(context: Context, exercise: String) {
    val videoMap = mapOf(
        "Pompes" to "android.resource://${context.packageName}/raw/video_roberto",
        "Squats" to "android.resource://${context.packageName}/raw/video_roberto",
        "Fentes" to "android.resource://${context.packageName}/raw/video_roberto",
        "Planche" to "android.resource://${context.packageName}/raw/video_roberto",
        "Tractions" to "android.resource://${context.packageName}/raw/video_roberto"
    )

    // Récupère l'URI de la vidéo correspondant à l'exercice
    val videoUri = videoMap[exercise] ?: return

    // Démarre l'activité VideoPlayerActivity avec l'URI
    val intent = Intent(context, VideoPlayerActivity::class.java).apply {
        putExtra("videoUri", videoUri) // Passe l'URI de la vidéo à l'activité
    }
    context.startActivity(intent)
}
