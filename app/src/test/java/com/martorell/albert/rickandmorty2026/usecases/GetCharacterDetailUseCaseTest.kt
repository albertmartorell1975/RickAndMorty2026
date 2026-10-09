package com.martorell.albert.rickandmorty2026.usecases

import com.martorell.albert.rickandmorty2026.domain.model.Result
import com.martorell.albert.rickandmorty2026.fakes.FakeCharacterRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetCharacterDetailUseCaseTest {

    private lateinit var fakeRepository: FakeCharacterRepository
    private lateinit var useCase: GetCharacterDetailUseCase

    @Before
    fun setUp() {
        fakeRepository = FakeCharacterRepository()
        useCase = GetCharacterDetailUseCase(fakeRepository)
    }

    @Test
    fun `invoke returns character and episodes successfully`() = runTest {
        // When
        val results = useCase(1).toList()

        // Then
        assertEquals(1, results.size)
        val successResult = results[0] as Result.Success
        val (character, episodes) = successResult.data
        
        assertEquals(1, character.id)
        assertEquals("Rick Sanchez", character.name)
        assertEquals(1, episodes.size)
        assertEquals("Pilot", episodes[0].name)
    }

    @Test
    fun `invoke returns error when repository fails`() = runTest {
        // Given
        fakeRepository.shouldReturnError = true

        // When
        val results = useCase(1).toList()

        // Then
        assertEquals(1, results.size)
        assertTrue(results[0] is Result.Error)
    }
}
