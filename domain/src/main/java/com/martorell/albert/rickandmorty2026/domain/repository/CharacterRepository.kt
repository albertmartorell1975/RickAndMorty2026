package com.martorell.albert.rickandmorty2026.domain.repository

import com.martorell.albert.rickandmorty2026.domain.model.Character
import com.martorell.albert.rickandmorty2026.domain.model.Episode
import com.martorell.albert.rickandmorty2026.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacters(
        page: Int,
        name: String? = null,
        status: String? = null,
        species: String? = null,
    ): Flow<Result<List<Character>>>

    fun getCharacterDetail(id: Int): Flow<Result<Character>>

    suspend fun getEpisodes(ids: List<Int>): Result<List<Episode>>

    suspend fun toggleFavorite(characterId: Int): Result<Unit>

    fun getFavoriteCharacters(): Flow<List<Character>>
}
