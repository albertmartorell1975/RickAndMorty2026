package com.martorell.albert.rickandmorty2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.martorell.albert.rickandmorty2026.navigation.Navigation
import com.martorell.albert.rickandmorty2026.ui.theme.RickAndMorty2026Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickAndMorty2026Theme {
                Navigation()
            }
        }
    }
}
