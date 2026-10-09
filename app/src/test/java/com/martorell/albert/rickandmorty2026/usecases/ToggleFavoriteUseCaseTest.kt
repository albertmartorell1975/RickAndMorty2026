package com.martorell.albert.rickandmorty2026.usecases

import com.martorell.albert.rickandmorty2026.domain.model.Result
import com.martorell.albert.rickandmorty2026.fakes.FakeCharacterRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ToggleFavoriteUseCaseTest {

    private lateinit var fakeRepository: FakeCharacterRepository
    private lateinit var useCase: ToggleFavoriteUseCase

    @Before
    fun setUp() {
        fakeRepository = FakeCharacterRepository()
        useCase = ToggleFavoriteUseCase(fakeRepository)
    }

    @Test
    fun `invoke toggles favorite status successfully`() = runTest {
        // Given
        val initialFavoriteStatus = fakeRepository.currentCharacter.isFavorite
        assertFalse(initialFavoriteStatus) // Setup starts with false
        
        // When
        val result = useCase(1)

        // Then
        assertTrue(result is Result.Success)
        assertTrue(fakeRepository.currentCharacter.isFavorite) // Should be toggled to true
    }
}
