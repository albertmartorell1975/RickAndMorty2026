package com.martorell.albert.rickandmorty2026.data.remote

import com.martorell.albert.rickandmorty2026.data.remote.dto.CharacterDto
import com.martorell.albert.rickandmorty2026.data.remote.dto.CharacterResponseDto
import com.martorell.albert.rickandmorty2026.data.remote.dto.EpisodeDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyDataSource {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int? = null,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null,
    ): CharacterResponseDto

    @GET("character/{id}")
    suspend fun getCharacterDetail(
        @Path("id") id: Int,
    ): CharacterDto

    @GET("episode/{ids}")
    suspend fun getEpisodes(
        @Path("ids") ids: String,
    ): List<EpisodeDto>
}
