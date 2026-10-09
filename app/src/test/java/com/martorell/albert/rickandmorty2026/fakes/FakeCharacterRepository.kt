package com.martorell.albert.rickandmorty2026.fakes

import com.martorell.albert.rickandmorty2026.domain.model.Character
import com.martorell.albert.rickandmorty2026.domain.model.CharacterCatalog
import com.martorell.albert.rickandmorty2026.domain.model.CharacterStatus
import com.martorell.albert.rickandmorty2026.domain.model.Episode
import com.martorell.albert.rickandmorty2026.domain.model.LocationRef
import com.martorell.albert.rickandmorty2026.domain.model.Result
import com.martorell.albert.rickandmorty2026.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeCharacterRepository : CharacterRepository {

    var shouldReturnError = false
    var currentCharacter = Character(
        id = 1,
        name = "Rick Sanchez",
        status = CharacterStatus.ALIVE,
        species = "Human",
        type = "",
        gender = "Male",
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        origin = LocationRef("Earth (C-137)", ""),
        location = LocationRef("Earth (Replacement Dimension)", ""),
        episodeUrls = listOf("https://rickandmortyapi.com/api/episode/1"),
        isFavorite = false
    )
    
    var currentEpisodes = listOf(
        Episode(
            id = 1,
            name = "Pilot",
            airDate = "December 2, 2013",
            episode = "S01E01",
            characterUrls = emptyList()
        )
    )

    override fun getCharacters(
        page: Int,
        name: String?,
        status: String?,
        species: String?
    ): Flow<Result<CharacterCatalog>> {
        return flowOf(Result.Success(CharacterCatalog(listOf(currentCharacter), 1, 1, 1)))
    }

    override fun getCharacterDetail(id: Int): Flow<Result<Character>> {
        if (shouldReturnError) {
            return flowOf(Result.Error(Exception("Test exception")))
        }
        return flowOf(Result.Success(currentCharacter.copy(id = id)))
    }

    override suspend fun getEpisodes(ids: List<Int>): Result<List<Episode>> {
        if (shouldReturnError) {
            return Result.Error(Exception("Test exception"))
        }
        return Result.Success(currentEpisodes)
    }

    override suspend fun toggleFavorite(characterId: Int): Result<Unit> {
        if (shouldReturnError) {
            return Result.Error(Exception("Test exception"))
        }
        currentCharacter = currentCharacter.copy(isFavorite = !currentCharacter.isFavorite)
        return Result.Success(Unit)
    }

    override fun getFavoriteCharacters(): Flow<List<Character>> {
        return flowOf(listOf(currentCharacter).filter { it.isFavorite })
    }
}
