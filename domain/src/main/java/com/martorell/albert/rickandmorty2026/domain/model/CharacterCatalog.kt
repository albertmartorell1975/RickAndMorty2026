package com.martorell.albert.rickandmorty2026.domain.model

data class CharacterCatalog(
    val characters: List<Character>,
    val totalCount: Int,
    val totalPages: Int,
    val currentPage: Int,
)
