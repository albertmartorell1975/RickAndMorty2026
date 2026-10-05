package com.martorell.albert.rickandmorty2026.data.di

import com.martorell.albert.rickandmorty2026.data.local.LocalDataSource
import com.martorell.albert.rickandmorty2026.data.local.LocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseBindsModule {

    @Binds
    @Singleton
    abstract fun bindLocalDataSource(
        impl: LocalDataSourceImpl,
    ): LocalDataSource
}
