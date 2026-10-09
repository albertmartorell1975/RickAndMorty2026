package com.martorell.albert.rickandmorty2026.ui.catalog

import com.martorell.albert.rickandmorty2026.fakes.FakeCharacterRepository
import com.martorell.albert.rickandmorty2026.usecases.GetFavoriteCharactersUseCase
import com.martorell.albert.rickandmorty2026.usecases.GetCharactersUseCase
import com.martorell.albert.rickandmorty2026.usecases.ToggleFavoriteUseCase
import com.martorell.albert.rickandmorty2026.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: CharacterListViewModel
    private lateinit var fakeRepository: FakeCharacterRepository

    @Before
    fun setUp() {
        fakeRepository = FakeCharacterRepository()
        
        val getCharactersUseCase = GetCharactersUseCase(fakeRepository)
        val getFavoriteCharactersUseCase = GetFavoriteCharactersUseCase(fakeRepository)
        val toggleFavoriteUseCase = ToggleFavoriteUseCase(fakeRepository)
        
        viewModel = CharacterListViewModel(
            getCharactersUseCase = getCharactersUseCase,
            getFavoriteCharactersUseCase = getFavoriteCharactersUseCase,
            toggleFavoriteUseCase = toggleFavoriteUseCase
        )
    }

    @Test
    fun `initial state loads characters successfully`() = runTest {
        // En llançar onStart() la primera vegada
        viewModel.onStart()

        val state = viewModel.state.value
        
        assertFalse(state.isLoadingInitial)
        assertTrue(state.characters.isNotEmpty())
        assertEquals(1, state.characters.size)
        assertEquals("Rick Sanchez", state.characters.first().name)
    }

    @Test
    fun `onStatusSelected updates selectedStatus and fetches filtered characters`() = runTest {
        // En canviar l'estat per filtrar
        viewModel.onStatusSelected(com.martorell.albert.rickandmorty2026.domain.model.CharacterStatus.DEAD)

        // El StateFlow ha de reflectir-ho i fer la càrrega inicial (per reinicialitzar la llista)
        val state = viewModel.state.value
        assertEquals(com.martorell.albert.rickandmorty2026.domain.model.CharacterStatus.DEAD, state.selectedStatus)
    }

    @Test
    fun `onFavoriteToggle updates character status`() = runTest {
        // En llançar onStart() la primera vegada
        viewModel.onStart()

        // Given un personatge inicial (Rick)
        val initialCharacters = viewModel.state.value.characters
        assertTrue(initialCharacters.isNotEmpty())
        val rick = initialCharacters.first()
        assertFalse(rick.isFavorite)

        // When toquem el favorit
        viewModel.onFavoriteToggle(rick)

        // Then s'ha d'actualitzar l'estat local a true
        val updatedCharacters = viewModel.state.value.characters
        assertTrue(updatedCharacters.first().isFavorite)
        
        // I el repositori fake també ho ha de rebre
        assertTrue(fakeRepository.currentCharacter.isFavorite)
    }
}
