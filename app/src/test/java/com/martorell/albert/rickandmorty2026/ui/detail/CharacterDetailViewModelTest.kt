package com.martorell.albert.rickandmorty2026.ui.detail

import androidx.lifecycle.SavedStateHandle
import com.martorell.albert.rickandmorty2026.fakes.FakeCharacterRepository
import com.martorell.albert.rickandmorty2026.usecases.GetCharacterDetailUseCase
import com.martorell.albert.rickandmorty2026.usecases.ToggleFavoriteUseCase
import com.martorell.albert.rickandmorty2026.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: CharacterDetailViewModel
    private lateinit var fakeRepository: FakeCharacterRepository

    @Before
    fun setUp() {
        fakeRepository = FakeCharacterRepository()
        
        val getCharacterDetailUseCase = GetCharacterDetailUseCase(fakeRepository)
        val toggleFavoriteUseCase = ToggleFavoriteUseCase(fakeRepository)
        
        // We pass the expected bundle arguments for the navigation route.
        // Destination.CharacterDetail(characterId = 1) is encoded as "characterId" in SavedStateHandle.
        val savedStateHandle = SavedStateHandle(mapOf("characterId" to 1))
        
        viewModel = CharacterDetailViewModel(
            savedStateHandle = savedStateHandle,
            getCharacterDetailUseCase = getCharacterDetailUseCase,
            toggleFavoriteUseCase = toggleFavoriteUseCase
        )
    }

    @Test
    fun `onStart loads character detail successfully`() = runTest {
        // When
        viewModel.onStart()

        // Then
        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertNotNull(state.character)
        assertEquals(1, state.character?.id)
        assertEquals("Rick Sanchez", state.character?.name)
        assertEquals(1, state.episodes.size)
        assertEquals("Pilot", state.episodes.first().name)
        assertNull(state.errorMessage)
    }

    @Test
    fun `onStart shows error when repository fails`() = runTest {
        // Given
        fakeRepository.shouldReturnError = true

        // When
        viewModel.onStart()

        // Then
        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertNull(state.character)
        assertNotNull(state.errorMessage)
    }

    @Test
    fun `onFavoriteToggle updates character favorite status and shows toast`() = runTest {
        // Given: load the character first
        viewModel.onStart()
        val initialState = viewModel.state.value
        assertFalse(initialState.character?.isFavorite ?: true)

        // When
        viewModel.onFavoriteToggle()

        // Then: character in UI state should be updated instantly
        val newState = viewModel.state.value
        assertTrue(newState.character?.isFavorite ?: false)
        assertNotNull(newState.toastMessage)
        
        // And the repository should be updated too
        assertTrue(fakeRepository.currentCharacter.isFavorite)
    }
    
    @Test
    fun `onToastDismissed clears toast message`() = runTest {
        // Given: trigger a toast
        viewModel.onStart()
        viewModel.onFavoriteToggle()
        assertNotNull(viewModel.state.value.toastMessage)
        
        // When
        viewModel.onToastDismissed()
        
        // Then
        assertNull(viewModel.state.value.toastMessage)
    }
}
