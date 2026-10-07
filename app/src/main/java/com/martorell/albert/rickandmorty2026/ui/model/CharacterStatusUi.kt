package com.martorell.albert.rickandmorty2026.ui.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.martorell.albert.rickandmorty2026.R
import com.martorell.albert.rickandmorty2026.domain.model.CharacterStatus
import com.martorell.albert.rickandmorty2026.ui.theme.StatusAlive
import com.martorell.albert.rickandmorty2026.ui.theme.StatusDead
import com.martorell.albert.rickandmorty2026.ui.theme.StatusUnknown

/**
 * Extension functions for the [CharacterStatus] domain enum.
 * This helper bridges the domain model with the UI layer by mapping domain status values
 * directly into their corresponding visual counterparts and localized string resources.
 */

/**
 * Maps the [CharacterStatus] to its corresponding visual status dot color token.
 */
fun CharacterStatus.toColor(): Color {
    return when (this) {
        CharacterStatus.ALIVE -> StatusAlive
        CharacterStatus.DEAD -> StatusDead
        CharacterStatus.UNKNOWN -> StatusUnknown
    }
}

/**
 * Maps the [CharacterStatus] to its localized string resource ID.
 */
@StringRes
fun CharacterStatus.toStringRes(): Int {
    return when (this) {
        CharacterStatus.ALIVE -> R.string.status_alive
        CharacterStatus.DEAD -> R.string.status_dead
        CharacterStatus.UNKNOWN -> R.string.status_unknown
    }
}
