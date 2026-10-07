package com.pemmob.videogame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pemmob.videogame.ui.navigation.AppNavHost
import com.pemmob.videogame.ui.theme.VideoGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VideoGameTheme(dynamicColor = false) {
                AppNavHost()
            }
        }
    }
}