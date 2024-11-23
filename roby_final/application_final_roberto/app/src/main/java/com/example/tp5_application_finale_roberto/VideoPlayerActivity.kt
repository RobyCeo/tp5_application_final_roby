package com.example.tp5_application_finale_roberto

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

class VideoPlayerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Récupère l'URI de la vidéo passée via l'intent
        val videoUri = intent.getStringExtra("videoUri")

        setContent {
            VideoPlayerScreen(videoUri = videoUri ?: "") {
                // Action pour revenir en arrière
                finish() // Ferme l'activité actuelle
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlayerScreen(videoUri: String, onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.TopStart
    ) {
        // Barre supérieure avec bouton de retour
        TopAppBar(
            title = { Text(text = "Lecture Vidéo", color = Color.White) },
            navigationIcon = {
                IconButton(onClick = { onBackClick() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack, // Icône Material Design pour retour
                        contentDescription = "Retour",
                        tint = Color.White
                    )
                }
            },
            colors = androidx.compose.material3.TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color.Black
            )
        )

        // Contenu principal avec la vidéo
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 56.dp), // Place la vidéo sous la barre supérieure
            contentAlignment = Alignment.Center
        ) {
            if (videoUri.isNotEmpty()) {
                val context = LocalContext.current
                AndroidView(
                    factory = {
                        VideoView(context).apply {
                            setVideoURI(Uri.parse(videoUri))

                            // Ajoute des contrôles pour naviguer dans la vidéo
                            val mediaController = MediaController(context)
                            mediaController.setAnchorView(this)
                            setMediaController(mediaController)

                            // Lance la vidéo automatiquement
                            setOnPreparedListener { start() }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
