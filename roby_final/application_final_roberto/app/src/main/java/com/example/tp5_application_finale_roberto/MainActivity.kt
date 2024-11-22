package com.example.tp5_application_finale_roberto

import HomePageWithNav
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import com.example.tp5_application_finale_roberto.ui.theme.Tp5_application_finale_robertoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tp5_application_finale_robertoTheme(
                darkTheme = isSystemInDarkTheme()
            ) {
                HomePageWithNav()
            }
        }

    }
}
