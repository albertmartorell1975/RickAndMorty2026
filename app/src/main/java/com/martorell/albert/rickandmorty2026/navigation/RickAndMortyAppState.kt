package com.martorell.albert.rickandmorty2026.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

/**
 * Creates and remembers the [RickAndMortyAppState] for the application.
 *
 * @param navController The [NavHostController] that manages app navigation.
 * @param snackbarHostState The global [SnackbarHostState] used to show snackbars across the app.
 */
@Composable
fun rememberRickAndMortyAppState(
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
): RickAndMortyAppState {
    return remember(navController, snackbarHostState) {
        RickAndMortyAppState(navController, snackbarHostState)
    }
}

/**
 * Hoists the UI and Navigation state for the global Scaffold.
 *
 * This allows the top-level app composable to react to route changes
 * (e.g., hiding or showing the TopAppBar based on the current destination)
 * and manage global UI elements like Snackbars.
 */
@Stable
class RickAndMortyAppState(
    val navController: NavHostController,
    val snackbarHostState: SnackbarHostState
) {
    /**
     * Gets the current navigation destination safely wrapped in a State.
     */
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    /**
     * Helper to navigate back.
     */
    fun navigateUp() {
        navController.navigateUp()
    }
}
