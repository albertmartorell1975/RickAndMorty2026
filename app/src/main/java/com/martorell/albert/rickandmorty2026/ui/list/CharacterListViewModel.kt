package com.martorell.albert.rickandmorty2026.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martorell.albert.rickandmorty2026.domain.model.Character
import com.martorell.albert.rickandmorty2026.domain.model.CharacterStatus
import com.martorell.albert.rickandmorty2026.domain.model.Result
import com.martorell.albert.rickandmorty2026.usecases.GetCharactersUseCase
import com.martorell.albert.rickandmorty2026.usecases.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
) : ViewModel() {

    // Inputs (UI Events)
    private val searchQueryFlow = MutableStateFlow("")
    private val selectedStatusFlow = MutableStateFlow<CharacterStatus?>(null)
    private val isRefreshingFlow = MutableStateFlow(false)
    private val pageFlow = MutableStateFlow(1)

    // Declarative State Stream
    val state = combine(
        searchQueryFlow,
        selectedStatusFlow,
        isRefreshingFlow,
        // The core data stream reacts to page and status changes
        combine(pageFlow, selectedStatusFlow) { page, status -> Pair(page, status) }
            .flatMapLatest { (page, status) ->
                getCharactersUseCase(page = page, status = status?.name?.lowercase())
            },
    ) { query, status, isRefreshing, result ->
        when (result) {
            is Result.Loading -> {
                UiState(
                    content = if (isRefreshing)
                        CharacterListContent.Success(emptyList())
                    else
                        CharacterListContent.Loading, // Keep old data if possible during refresh, or show main loader
                    searchQuery = query,
                    selectedStatus = status,
                    isRefreshing = isRefreshing,
                )
            }

            is Result.Success -> {
                val trimmedQuery = query.trim()
                val filtered = if (trimmedQuery.isEmpty()) {
                    result.data
                } else {
                    result.data.filter { it.name.contains(trimmedQuery, ignoreCase = true) }
                }

                // Clear refreshing flag once data succeeds
                if (isRefreshing) isRefreshingFlow.value = false

                UiState(
                    content = CharacterListContent.Success(filtered),
                    searchQuery = query,
                    selectedStatus = status,
                    isRefreshing = false,
                )
            }

            is Result.Error -> {
                if (isRefreshing) isRefreshingFlow.value = false
                UiState(
                    content = CharacterListContent.Error(
                        result.exception.message ?: "Unknown error occurred",
                    ),
                    searchQuery = query,
                    selectedStatus = status,
                    isRefreshing = false,
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000), // Rotation-safe
        initialValue = UiState(), // Initial state before collection starts
    )

    data class UiState(
        val content: CharacterListContent = CharacterListContent.Loading,
        val searchQuery: String = "",
        val selectedStatus: CharacterStatus? = null, // null = "All"
        val isRefreshing: Boolean = false,
    )

    sealed interface CharacterListContent {
        data object Loading : CharacterListContent
        data class Success(val characters: List<Character>) : CharacterListContent
        data class Error(val message: String) : CharacterListContent
    }

    // UI Actions

    fun onStart() {
        // With stateIn, the initial load happens automatically on subscription.
        // We can optionally use this to reset states if we navigated back to a failed state.
        if (state.value.content is CharacterListContent.Error) {
            pageFlow.value = 1
        }
    }

    fun onRefresh() {
        isRefreshingFlow.value = true
        pageFlow.value = 1
    }

    fun onSearchQueryChanged(query: String) {
        searchQueryFlow.value = query
    }

    fun onStatusSelected(status: CharacterStatus?) {
        selectedStatusFlow.value = status
        pageFlow.value = 1
    }

    fun onFavoriteToggle(character: Character) {
        viewModelScope.launch {
            toggleFavoriteUseCase(character.id)
        }
    }
}
