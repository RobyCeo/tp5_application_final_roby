package com.example.tp5_application_finale_roberto

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.ComponentActivity

class VideoPlayerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialise le VideoView
        val videoView = VideoView(this)
        setContentView(videoView)

        // Récupère l'URI de la vidéo depuis l'intent
        val videoUri = intent.getStringExtra("videoUri")
        if (videoUri != null) {
            // Configure le VideoView avec l'URI
            videoView.setVideoURI(Uri.parse(videoUri))

            // Ajoute des contrôles pour navigation dans la vidéo
            val mediaController = MediaController(this)
            mediaController.setAnchorView(videoView)
            videoView.setMediaController(mediaController)

            // Lance la vidéo automatiquement
            videoView.setOnPreparedListener { videoView.start() }

            // Gère les erreurs éventuelles
            videoView.setOnErrorListener { _, what, extra ->
                // Ajoute une logique de gestion des erreurs (optionnel)
                false
            }
        }
    }
}
