package com.martorell.albert.rickandmorty2026.domain.model

data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residentUrls: List<String>,
)
