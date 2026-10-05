package com.martorell.albert.rickandmorty2026.data.di

import android.content.Context
import androidx.room.Room
import com.martorell.albert.rickandmorty2026.data.local.RickAndMortyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATABASE_NAME = "rick_and_morty_db"

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): RickAndMortyDatabase {
        return Room.databaseBuilder(
            context,
            RickAndMortyDatabase::class.java,
            DATABASE_NAME,
        )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

}
