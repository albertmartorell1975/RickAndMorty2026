package com.martorell.albert.rickandmorty2026.usecases

import com.martorell.albert.rickandmorty2026.domain.model.CharacterCatalog
import com.martorell.albert.rickandmorty2026.domain.model.Result
import com.martorell.albert.rickandmorty2026.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: CharacterRepository,
) {
    operator fun invoke(
        page: Int,
        status: String? = null,
        species: String? = null,
    ): Flow<Result<CharacterCatalog>> {
        return repository.getCharacters(
            page = page,
            status = status,
            species = species,
        )
    }
}
