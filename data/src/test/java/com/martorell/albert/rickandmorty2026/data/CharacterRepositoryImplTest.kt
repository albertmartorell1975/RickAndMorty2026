package com.martorell.albert.rickandmorty2026.data

import com.martorell.albert.rickandmorty2026.data.local.LocalDataSource
import com.martorell.albert.rickandmorty2026.data.local.entity.CharacterEntity
import com.martorell.albert.rickandmorty2026.data.remote.RickAndMortyDataSource
import com.martorell.albert.rickandmorty2026.data.remote.dto.CharacterDto
import com.martorell.albert.rickandmorty2026.data.remote.dto.CharacterResponseDto
import com.martorell.albert.rickandmorty2026.data.remote.dto.EpisodeDto
import com.martorell.albert.rickandmorty2026.data.remote.dto.LocationRefDto
import com.martorell.albert.rickandmorty2026.data.remote.dto.PageInfoDto
import com.martorell.albert.rickandmorty2026.data.repository.CharacterRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CharacterRepositoryImplTest {

    private lateinit var fakeApi: FakeRickAndMortyDataSource
    private lateinit var fakeLocalDataSource: FakeCharacterLocalDataSource
    private lateinit var repository: CharacterRepositoryImpl

    @Before
    fun setUp() {
        fakeApi = FakeRickAndMortyDataSource()
        fakeLocalDataSource = FakeCharacterLocalDataSource()
        repository = CharacterRepositoryImpl(fakeApi, fakeLocalDataSource)
    }

    @Test
    fun getCharacters_emitsSuccess_whenApiReturnsData() = runTest {
        val dto = CharacterDto(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = LocationRefDto("Earth", "url1"),
            location = LocationRefDto("Citadel", "url2"),
            image = "img_url",
            episode = listOf("ep1"),
        )
        fakeApi.charactersResponse = CharacterResponseDto(
            info = PageInfoDto(1, 1, null, null),
            results = listOf(dto),
        )

        val result = repository.getCharacters(page = 1).first()

        assertTrue(result.isSuccess)
        val list = result.getOrNull()
        assertEquals(1, list?.size)
        assertEquals("Rick Sanchez", list?.first()?.name)
    }

    @Test
    fun getEpisodes_returnsSuccess_whenApiReturnsEpisodes() = runTest {
        val episodeDto = EpisodeDto(
            id = 1,
            name = "Pilot",
            airDate = "Dec 2, 2013",
            episode = "S01E01",
            characters = listOf("url1"),
        )
        fakeApi.episodesResponse = listOf(episodeDto)

        val result = repository.getEpisodes(listOf(1))

        assertTrue(result.isSuccess)
        val list = result.getOrNull()
        assertEquals(1, list?.size)
        assertEquals("Pilot", list?.first()?.name)
    }

    @Test
    fun toggleFavorite_togglesExistingFavoriteStatus() = runTest {
        val entity = CharacterEntity(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            image = "img",
            originName = "Earth",
            originUrl = "url",
            locationName = "Earth",
            locationUrl = "url",
            episodeUrls = "ep1",
            isFavorite = false,
        )
        fakeLocalDataSource.insertCharacter(entity)

        val result = repository.toggleFavorite(1)

        assertTrue(result.isSuccess)
        val updated = fakeLocalDataSource.getCharacterById(1)
        assertEquals(true, updated?.isFavorite)
    }
}

private class FakeRickAndMortyDataSource : RickAndMortyDataSource {
    var charactersResponse: CharacterResponseDto = CharacterResponseDto(null, emptyList())
    var episodesResponse: List<EpisodeDto> = emptyList()

    override suspend fun getCharacters(
        page: Int?,
        name: String?,
        status: String?,
        species: String?,
    ): CharacterResponseDto = charactersResponse

    override suspend fun getCharacterDetail(id: Int): CharacterDto {
        return charactersResponse.results?.first { it.id == id }
            ?: throw NoSuchElementException("Character not found")
    }

    override suspend fun getEpisodes(ids: String): List<EpisodeDto> = episodesResponse
}

private class FakeCharacterLocalDataSource : LocalDataSource {
    private val db = mutableMapOf<Int, CharacterEntity>()

    override suspend fun getCharacterById(id: Int): CharacterEntity? = db[id]

    override fun getFavoriteCharacters(): Flow<List<CharacterEntity>> =
        flowOf(db.values.filter { it.isFavorite })

    override suspend fun insertCharacters(characters: List<CharacterEntity>) {
        characters.forEach { db[it.id] = it }
    }

    override suspend fun insertCharacter(character: CharacterEntity) {
        db[character.id] = character
    }

    override suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean): Int {
        val existing = db[id] ?: return 0
        db[id] = existing.copy(isFavorite = isFavorite)
        return 1
    }
}
