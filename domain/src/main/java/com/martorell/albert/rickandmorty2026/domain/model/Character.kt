package com.martorell.albert.rickandmorty2026.domain.model

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val image: String,
    val origin: LocationRef,
    val location: LocationRef,
    val episodeUrls: List<String>,
    val isFavorite: Boolean = false,
)
