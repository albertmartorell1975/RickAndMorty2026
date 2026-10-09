package com.martorell.albert.rickandmorty2026.domain.model

enum class CharacterStatus {
    ALIVE,
    DEAD,
    UNKNOWN;

    companion object {
        fun fromString(value: String?): CharacterStatus {
            return when (value?.lowercase()) {
                "alive" -> ALIVE
                "dead" -> DEAD
                else -> UNKNOWN
            }
        }
    }
}
