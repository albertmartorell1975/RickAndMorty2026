package com.martorell.albert.rickandmorty2026.data.local

import com.martorell.albert.rickandmorty2026.data.local.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun getCharacterById(id: Int): CharacterEntity?
    fun getCharacterByIdFlow(id: Int): Flow<CharacterEntity?>
    fun getFavoriteCharacters(): Flow<List<CharacterEntity>>
    suspend fun insertCharacters(characters: List<CharacterEntity>)
    suspend fun insertCharacter(character: CharacterEntity)
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean): Int

}
