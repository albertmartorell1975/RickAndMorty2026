package com.martorell.albert.rickandmorty2026.data.di

import com.martorell.albert.rickandmorty2026.data.repository.CharacterRepositoryImpl
import com.martorell.albert.rickandmorty2026.domain.repository.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindsModule {

    @Binds
    @Singleton
    abstract fun bindCharacterRepository(
        impl: CharacterRepositoryImpl,
    ): CharacterRepository
}
