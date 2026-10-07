package com.martorell.albert.rickandmorty2026.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.martorell.albert.rickandmorty2026.ui.list.CharacterListScreen

/**
 * The top-level composition representing the entire Navigation architecture.
 *
 * It provides the global [Scaffold] and the [NavHost] where routing happens.
 */
@Composable
fun Navigation(
    appState: RickAndMortyAppState = rememberRickAndMortyAppState()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        NavHost(
            navController = appState.navController,
            startDestination = Destination.CharacterList
        ) {
            composable<Destination.CharacterList> {
                CharacterListScreen(
                    contentPadding = innerPadding,
                    onCharacterClicked = { characterId ->
                        appState.navController.navigate(Destination.CharacterDetail(characterId))
                    }
                )
            }
            
            // The Detail screen composable will be added here once implemented.
            // composable<Destination.CharacterDetail> { backStackEntry ->
            //    val detail = backStackEntry.toRoute<Destination.CharacterDetail>()
            //    CharacterDetailScreen(characterId = detail.characterId)
            // }
        }
    }
}
