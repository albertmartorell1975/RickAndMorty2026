package com.martorell.albert.rickandmorty2026.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.martorell.albert.rickandmorty2026.R
import com.martorell.albert.rickandmorty2026.domain.model.Character
import com.martorell.albert.rickandmorty2026.domain.model.Episode
import com.martorell.albert.rickandmorty2026.domain.model.Result
import com.martorell.albert.rickandmorty2026.navigation.Destination
import com.martorell.albert.rickandmorty2026.ui.util.UiText
import com.martorell.albert.rickandmorty2026.usecases.GetCharacterDetailUseCase
import com.martorell.albert.rickandmorty2026.usecases.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
) : ViewModel() {

    private val characterId: Int =
        savedStateHandle.toRoute<Destination.CharacterDetail>().characterId

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    data class UiState(
        val isLoading: Boolean = true,
        val character: Character? = null,
        val episodes: List<Episode> = emptyList(),
        val errorMessage: UiText? = null,
        val toastMessage: UiText? = null
    )

    fun onStart() {
        if (_state.value.character == null) {
            _state.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null,
                )
            }
            loadCharacterDetail()
        }
    }

    fun onRetry() {
        _state.update {
            it.copy(
                isLoading = true,
                errorMessage = null,
            )
        }
        loadCharacterDetail()
    }

    fun onFavoriteToggle() {
        val currentCharacter = _state.value.character ?: return
        val newFavoriteState = !currentCharacter.isFavorite

        _state.update { currentState ->
            currentState.copy(
                character = currentCharacter.copy(isFavorite = newFavoriteState),
                toastMessage = UiText.StringResource(R.string.toast_character_state_updated)
            )
        }

        viewModelScope.launch {
            toggleFavoriteUseCase(currentCharacter.id)
        }
    }

    fun onToastDismissed() {
        _state.update { it.copy(toastMessage = null) }
    }

    private fun loadCharacterDetail() {
        viewModelScope.launch {
            getCharacterDetailUseCase(characterId).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }

                    is Result.Success -> {
                        val (character, episodes) = result.data
                        _state.update {
                            it.copy(
                                isLoading = false,
                                character = character,
                                episodes = episodes,
                                errorMessage = null,
                            )
                        }
                    }

                    is Result.Error -> {
                        val message = result.exception.message
                        val uiText = if (!message.isNullOrBlank()) {
                            UiText.DynamicString(message)
                        } else {
                            UiText.StringResource(R.string.error_unknown)
                        }
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = uiText,
                            )
                        }
                    }
                }
            }
        }
    }
}