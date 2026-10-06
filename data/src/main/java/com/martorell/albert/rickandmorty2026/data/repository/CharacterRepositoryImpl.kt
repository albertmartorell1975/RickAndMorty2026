package com.martorell.albert.rickandmorty2026.data.repository

import com.martorell.albert.rickandmorty2026.data.local.LocalDataSource
import com.martorell.albert.rickandmorty2026.data.mapper.toDomain
import com.martorell.albert.rickandmorty2026.data.mapper.toEntity
import com.martorell.albert.rickandmorty2026.data.remote.RickAndMortyDataSource
import com.martorell.albert.rickandmorty2026.data.util.safeCall
import com.martorell.albert.rickandmorty2026.domain.model.Character
import com.martorell.albert.rickandmorty2026.domain.model.Episode
import com.martorell.albert.rickandmorty2026.domain.model.Result
import com.martorell.albert.rickandmorty2026.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val serverDataSource: RickAndMortyDataSource,
    private val localDataSource: LocalDataSource,
) : CharacterRepository {

    override fun getCharacters(
        page: Int,
        name: String?,
        status: String?,
        species: String?,
    ): Flow<Result<List<Character>>> = flow {
        val result = safeCall {
            val response = serverDataSource.getCharacters(
                page = page,
                name = name,
                status = status,
                species = species
            )
            val characters = response.results ?: emptyList()

            val entities = characters.map { dto ->
                val existingLocal = dto.id?.let { localDataSource.getCharacterById(it) }
                val isFav = existingLocal?.isFavorite ?: false
                dto.toEntity(isFavorite = isFav)
            }

            if (entities.isNotEmpty()) {
                localDataSource.insertCharacters(entities)
            }

            entities.map { it.toDomain() }
        }
        emit(result)
    }

    override fun getCharacterDetail(id: Int): Flow<Result<Character>> = flow {
        // 1. Local read protected with safeCall
        val localResult = safeCall { localDataSource.getCharacterById(id) }
        val localCharacter = (localResult as? Result.Success)?.data

        if (localCharacter != null) {
            emit(Result.Success(localCharacter.toDomain()))
        }

        // 2. Remote request
        val remoteResult = safeCall {
            val remoteDto = serverDataSource.getCharacterDetail(id)
            val isFav = localCharacter?.isFavorite ?: false
            val updatedEntity = remoteDto.toEntity(isFavorite = isFav)
            localDataSource.insertCharacter(updatedEntity)
            updatedEntity.toDomain()
        }

        // 3. Emit remote result
        emit(remoteResult)
    }

    override suspend fun getEpisodes(ids: List<Int>): Result<List<Episode>> {
        if (ids.isEmpty()) return Result.Success(emptyList())
        return safeCall {
            val idsQuery = if (ids.size == 1) {
                "${ids.first()},${ids.first()}"
            } else {
                ids.joinToString(",")
            }
            val dtoList = serverDataSource.getEpisodes(idsQuery)
            dtoList.distinctBy { it.id }.map { it.toDomain() }
        }
    }

    override suspend fun toggleFavorite(characterId: Int): Result<Unit> {
        return safeCall {
            val character = localDataSource.getCharacterById(characterId)
            if (character != null) {
                val newFavStatus = !character.isFavorite
                localDataSource.updateFavoriteStatus(characterId, newFavStatus)
            } else {
                val remoteDto = serverDataSource.getCharacterDetail(characterId)
                val entity = remoteDto.toEntity(isFavorite = true)
                localDataSource.insertCharacter(entity)
            }
        }
    }

    override fun getFavoriteCharacters(): Flow<List<Character>> {
        return localDataSource.getFavoriteCharacters().map { list ->
            list.map { it.toDomain() }
        }
    }
}
