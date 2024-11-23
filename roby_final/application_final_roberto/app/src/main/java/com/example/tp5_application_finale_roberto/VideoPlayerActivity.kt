package com.example.tp5_application_finale_roberto

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.activity.ComponentActivity

class VideoPlayerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val videoView = VideoView(this)
        setContentView(videoView)

        val videoUri = intent.getStringExtra("videoUri")
        videoView.setVideoURI(Uri.parse(videoUri))
        videoView.setOnPreparedListener { videoView.start() }
    }
}
