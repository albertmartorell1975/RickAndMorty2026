package com.martorell.albert.rickandmorty2026.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.martorell.albert.rickandmorty2026.ui.catalog.CharacterListScreen
import com.martorell.albert.rickandmorty2026.ui.detail.CharacterDetailScreen

/**
 * The top-level composition representing the entire Navigation architecture.
 *
 * It provides the top-level container [Box] and the [NavHost] where routing happens.
 */
@Composable
fun Navigation(
    appState: RickAndMortyAppState = rememberRickAndMortyAppState()
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(
            navController = appState.navController,
            startDestination = Destination.CharacterList
        ) {
            composable<Destination.CharacterList> {
                CharacterListScreen(
                    onCharacterClicked = { characterId ->
                        appState.navController.navigate(Destination.CharacterDetail(characterId))
                    }
                )
            }

            composable<Destination.CharacterDetail> {
                CharacterDetailScreen(
                    snackbarHostState = appState.snackbarHostState,
                    onBackClicked = { appState.navigateUp() }
                )
            }
        }

        // Snackbars are global, "survive" the navigation transitions
        SnackbarHost(
            hostState = appState.snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .systemBarsPadding()
        )
    }
}
