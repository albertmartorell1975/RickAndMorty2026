package com.martorell.albert.rickandmorty2026.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.martorell.albert.rickandmorty2026.data.local.dao.CharacterDao
import com.martorell.albert.rickandmorty2026.data.local.entity.CharacterEntity

@Database(
    entities = [
        CharacterEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class RickAndMortyDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}
