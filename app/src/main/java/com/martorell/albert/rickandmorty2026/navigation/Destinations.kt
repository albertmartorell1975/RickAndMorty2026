package com.martorell.albert.rickandmorty2026.navigation

import kotlinx.serialization.Serializable

/**
 * Type-safe navigation routes.
 * Using serialization ensures compile-time safety when passing arguments between Jetpack Compose destinations.
 */
sealed interface Destination {

    /**
     * Master catalog screen displaying characters with filtering and pagination.
     */
    @Serializable
    data object CharacterList : Destination

    /**
     * Detail screen for a specific character.
     *
     * @param characterId The unique identifier of the character to display.
     */
    @Serializable
    data class CharacterDetail(val characterId: Int) : Destination
}