package com.martorell.albert.rickandmorty2026.data.local

import com.martorell.albert.rickandmorty2026.data.local.dao.CharacterDao
import com.martorell.albert.rickandmorty2026.data.local.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(db: RickAndMortyDatabase) : LocalDataSource {

    private val characterDao: CharacterDao = db.characterDao()

    override suspend fun getCharacterById(id: Int): CharacterEntity? =
        characterDao.getCharacterById(id)

    override fun getFavoriteCharacters(): Flow<List<CharacterEntity>> =
        characterDao.getFavoriteCharacters()

    override suspend fun insertCharacters(characters: List<CharacterEntity>) =
        characterDao.insertCharacters(characters)

    override suspend fun insertCharacter(character: CharacterEntity) =
        characterDao.insertCharacter(character)

    override suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean): Int =
        characterDao.updateFavoriteStatus(id, isFavorite)

}
