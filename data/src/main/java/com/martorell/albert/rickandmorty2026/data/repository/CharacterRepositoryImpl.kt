package com.martorell.albert.rickandmorty2026.data.repository

import com.martorell.albert.rickandmorty2026.data.local.LocalDataSource
import com.martorell.albert.rickandmorty2026.data.mapper.toDomain
import com.martorell.albert.rickandmorty2026.data.mapper.toEntity
import com.martorell.albert.rickandmorty2026.data.remote.RickAndMortyDataSource
import com.martorell.albert.rickandmorty2026.domain.model.Character
import com.martorell.albert.rickandmorty2026.domain.model.Episode
import com.martorell.albert.rickandmorty2026.domain.repository.CharacterRepository
import kotlinx.coroutines.CancellationException
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
        try {
            val response = serverDataSource.getCharacters(page = page, name = name, status = status, species = species)
            val dtos = response.results ?: emptyList()

            val entities = dtos.map { dto ->
                val existingLocal = dto.id?.let { localDataSource.getCharacterById(it) }
                val isFav = existingLocal?.isFavorite ?: false
                dto.toEntity(isFavorite = isFav)
            }

            if (entities.isNotEmpty()) {
                localDataSource.insertCharacters(entities)
            }

            val domainCharacters = entities.map { it.toDomain() }
            emit(Result.success(domainCharacters))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun getCharacterDetail(id: Int): Flow<Result<Character>> = flow {
        val cached = localDataSource.getCharacterById(id)
        if (cached != null) {
            emit(Result.success(cached.toDomain()))
        }

        try {
            val remoteDto = serverDataSource.getCharacterDetail(id)
            val existing = localDataSource.getCharacterById(id)
            val isFav = existing?.isFavorite ?: false
            val updatedEntity = remoteDto.toEntity(isFavorite = isFav)
            localDataSource.insertCharacter(updatedEntity)
            emit(Result.success(updatedEntity.toDomain()))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            if (cached == null) {
                emit(Result.failure(e))
            }
        }
    }

    override suspend fun getEpisodes(ids: List<Int>): Result<List<Episode>> {
        if (ids.isEmpty()) return Result.success(emptyList())
        return try {
            val idsQuery = if (ids.size == 1) {
                "${ids.first()},${ids.first()}"
            } else {
                ids.joinToString(",")
            }
            val dtoList = serverDataSource.getEpisodes(idsQuery)
            val episodes = dtoList.distinctBy { it.id }.map { it.toDomain() }
            Result.success(episodes)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun toggleFavorite(characterId: Int): Result<Unit> {
        return try {
            val cached = localDataSource.getCharacterById(characterId)
            if (cached != null) {
                val newFavStatus = !cached.isFavorite
                localDataSource.updateFavoriteStatus(characterId, newFavStatus)
            } else {
                val remoteDto = serverDataSource.getCharacterDetail(characterId)
                val entity = remoteDto.toEntity(isFavorite = true)
                localDataSource.insertCharacter(entity)
            }
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getFavoriteCharacters(): Flow<List<Character>> {
        return localDataSource.getFavoriteCharacters().map { list ->
            list.map { it.toDomain() }
        }
    }
}
