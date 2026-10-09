package com.martorell.albert.rickandmorty2026.data.remote.dto

data class CharacterDto(
    val id: Int?,
    val name: String?,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    val origin: LocationRefDto?,
    val location: LocationRefDto?,
    val image: String?,
    val episode: List<String>?,
)

data class LocationRefDto(
    val name: String?,
    val url: String?,
)
