package com.martorell.albert.rickandmorty2026.usecases

import com.martorell.albert.rickandmorty2026.domain.model.Character
import com.martorell.albert.rickandmorty2026.domain.model.Episode
import com.martorell.albert.rickandmorty2026.domain.model.Result
import com.martorell.albert.rickandmorty2026.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCharacterDetailUseCase @Inject constructor(
    private val repository: CharacterRepository,
) {
    operator fun invoke(id: Int): Flow<Result<Pair<Character, List<Episode>>>> = flow {
        repository.getCharacterDetail(id).collect { charResult ->
            when (charResult) {
                is Result.Success -> {
                    val character = charResult.data
                    val episodeIds = character.episodeUrls.mapNotNull { url ->
                        url.substringAfterLast("/").toIntOrNull()
                    }
                    when (val episodesResult = repository.getEpisodes(episodeIds)) {
                        is Result.Success -> {
                            emit(Result.Success(Pair(character, episodesResult.data)))
                        }
                        is Result.Error -> {
                            emit(Result.Success(Pair(character, emptyList())))
                        }
                        is Result.Loading -> {
                            emit(Result.Loading)
                        }
                    }
                }
                is Result.Error -> {
                    emit(charResult)
                }
                is Result.Loading -> {
                    emit(Result.Loading)
                }
            }
        }
    }
}
