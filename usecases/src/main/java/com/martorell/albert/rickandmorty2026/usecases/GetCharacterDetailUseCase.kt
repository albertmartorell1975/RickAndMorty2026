package com.martorell.albert.rickandmorty2026.usecases

import com.martorell.albert.rickandmorty2026.domain.model.Character
import com.martorell.albert.rickandmorty2026.domain.model.Episode
import com.martorell.albert.rickandmorty2026.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCharacterDetailUseCase @Inject constructor(
    private val repository: CharacterRepository,
) {
    operator fun invoke(id: Int): Flow<Result<Pair<Character, List<Episode>>>> = flow {
        repository.getCharacterDetail(id).collect { charResult ->
            charResult.fold(
                onSuccess = { character ->
                    val episodeIds = character.episodeUrls.mapNotNull { url ->
                        url.substringAfterLast("/").toIntOrNull()
                    }
                    val episodesResult = repository.getEpisodes(episodeIds)
                    episodesResult.fold(
                        onSuccess = { episodes ->
                            emit(Result.success(Pair(character, episodes)))
                        },
                        onFailure = { _ ->
                            emit(Result.success(Pair(character, emptyList())))
                        }
                    )
                },
                onFailure = { error ->
                    emit(Result.failure(error))
                }
            )
        }
    }
}
