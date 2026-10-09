package com.martorell.albert.rickandmorty2026.domain.model

data class Episode(
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
    val characterUrls: List<String>,
)
