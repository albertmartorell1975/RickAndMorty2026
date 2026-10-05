package com.martorell.albert.rickandmorty2026.usecases

import com.martorell.albert.rickandmorty2026.domain.repository.CharacterRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: CharacterRepository,
) {
    suspend operator fun invoke(characterId: Int): Result<Unit> {
        return repository.toggleFavorite(characterId)
    }
}
