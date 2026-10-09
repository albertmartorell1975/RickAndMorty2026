package com.martorell.albert.rickandmorty2026.data.mapper

import com.martorell.albert.rickandmorty2026.data.local.entity.CharacterEntity
import com.martorell.albert.rickandmorty2026.data.remote.dto.CharacterDto
import com.martorell.albert.rickandmorty2026.data.remote.dto.EpisodeDto
import com.martorell.albert.rickandmorty2026.data.remote.dto.LocationRefDto
import com.martorell.albert.rickandmorty2026.domain.model.Character
import com.martorell.albert.rickandmorty2026.domain.model.CharacterStatus
import com.martorell.albert.rickandmorty2026.domain.model.Episode
import com.martorell.albert.rickandmorty2026.domain.model.LocationRef

// LocationRef mappers
fun LocationRefDto?.toDomain(): LocationRef {
    return LocationRef(
        name = this?.name ?: "",
        url = this?.url ?: "",
    )
}

// Character mappers
fun CharacterDto.toDomain(isFavorite: Boolean = false): Character {
    return Character(
        id = id ?: 0,
        name = name ?: "",
        status = CharacterStatus.fromString(status),
        species = species ?: "",
        type = type ?: "",
        gender = gender ?: "",
        image = image ?: "",
        origin = origin.toDomain(),
        location = location.toDomain(),
        episodeUrls = episode ?: emptyList(),
        isFavorite = isFavorite,
    )
}

fun CharacterDto.toEntity(isFavorite: Boolean = false): CharacterEntity {
    return CharacterEntity(
        id = id ?: 0,
        name = name ?: "",
        status = status ?: "",
        species = species ?: "",
        type = type ?: "",
        gender = gender ?: "",
        image = image ?: "",
        originName = origin?.name ?: "",
        originUrl = origin?.url ?: "",
        locationName = location?.name ?: "",
        locationUrl = location?.url ?: "",
        episodeUrls = episode?.joinToString(",") ?: "",
        isFavorite = isFavorite,
    )
}

fun CharacterEntity.toDomain(): Character {
    return Character(
        id = id,
        name = name,
        status = CharacterStatus.fromString(status),
        species = species,
        type = type,
        gender = gender,
        image = image,
        origin = LocationRef(originName, originUrl),
        location = LocationRef(locationName, locationUrl),
        episodeUrls = if (episodeUrls.isEmpty()) emptyList() else episodeUrls.split(","),
        isFavorite = isFavorite,
    )
}

fun Character.toEntity(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        status = status.name, // Save Enum as String to DB
        species = species,
        type = type,
        gender = gender,
        image = image,
        originName = origin.name,
        originUrl = origin.url,
        locationName = location.name,
        locationUrl = location.url,
        episodeUrls = episodeUrls.joinToString(","),
        isFavorite = isFavorite,
    )
}

// Episode mappers
fun EpisodeDto.toDomain(): Episode {
    return Episode(
        id = id,
        name = name,
        airDate = airDate ?: "",
        episode = episode ?: "",
        characterUrls = characters ?: emptyList(),
    )
}
