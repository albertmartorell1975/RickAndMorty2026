package com.martorell.albert.rickandmorty2026.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martorell.albert.rickandmorty2026.R
import com.martorell.albert.rickandmorty2026.domain.model.Character
import com.martorell.albert.rickandmorty2026.domain.model.CharacterStatus
import com.martorell.albert.rickandmorty2026.domain.model.Result
import com.martorell.albert.rickandmorty2026.ui.util.UiText
import com.martorell.albert.rickandmorty2026.usecases.GetCharactersUseCase
import com.martorell.albert.rickandmorty2026.usecases.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    private var fetchJob: Job? = null

    data class UiState(
        val characters: List<Character> = emptyList(),
        val selectedStatus: CharacterStatus? = null,
        val isRefreshing: Boolean = false,
        val isLoadingNextPage: Boolean = false,
        val isLoadingInitial: Boolean = true,
        val isRetryingInitial: Boolean = false,
        val isRetryingPagination: Boolean = false,
        val errorMessage: UiText? = null,
        val totalCount: Int = 0,
        val totalPages: Int = 0,
        val currentPage: Int = 1,
    )

    fun onStart() {
        if (_state.value.characters.isEmpty() && _state.value.errorMessage == null) {
            _state.update { it.copy(isLoadingInitial = true) }
            loadCharacters(page = 1)
        }
    }

    fun onRetry() {
        _state.update {
            it.copy(
                isLoadingInitial = true,
                isRetryingInitial = true,
                errorMessage = null
            )
        }
        loadCharacters(page = 1, clearExisting = true)
    }

    fun onRefresh() {
        _state.update {
            it.copy(
                isRefreshing = true,
                errorMessage = null
            )
        }
        loadCharacters(page = 1, clearExisting = true)
    }

    fun onStatusSelected(status: CharacterStatus?) {
        if (_state.value.selectedStatus == status) return
        _state.update {
            it.copy(
                selectedStatus = status,
                isLoadingInitial = true,
                errorMessage = null
            )
        }
        loadCharacters(page = 1, clearExisting = true)
    }

    fun onLoadMore() {
        val currentState = _state.value
        if (currentState.isLoadingNextPage || currentState.isRefreshing || currentState.isLoadingInitial) return
        if (currentState.currentPage >= currentState.totalPages) return

        val isRetrying = currentState.errorMessage != null
        _state.update {
            it.copy(
                isLoadingNextPage = true,
                isRetryingPagination = isRetrying,
                errorMessage = null
            )
        }
        loadCharacters(page = currentState.currentPage + 1)
    }

    fun onFavoriteToggle(character: Character) {
        _state.update { currentState ->
            val updatedList = currentState.characters.map {
                if (it.id == character.id) it.copy(isFavorite = !it.isFavorite) else it
            }
            currentState.copy(characters = updatedList)
        }

        viewModelScope.launch {
            toggleFavoriteUseCase(character.id)
        }
    }

    private fun loadCharacters(page: Int, clearExisting: Boolean = false) {
        // Si NO estem reiniciant la llista (és a dir, estem fent Paginació)
        // i ja hi ha una tasca activa, simplement sortim per no tallar-la.
        if (!clearExisting && fetchJob?.isActive == true) return

        // Si estem fent un "clearExisting" (Filtres, Refresh, Retry),
        // llavors sí que cancel·lem de manera segura perquè anem a la pàgina 1.
        if (clearExisting) {
            fetchJob?.cancel()
        }

        fetchJob = viewModelScope.launch {
            val currentStatus = _state.value.selectedStatus?.name?.lowercase()

            getCharactersUseCase(page = page, status = currentStatus).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        // We already handle loading flags in the actions. So, we are just waiting.
                    }

                    is Result.Success -> {
                        val catalog = result.data

                        _state.update { currentState ->
                            val newList = if (clearExisting || page == 1) {
                                catalog.characters
                            } else {
                                val newMap = catalog.characters.associateBy { it.id }
                                val updatedExisting =
                                    currentState.characters.map { newMap[it.id] ?: it }
                                val existingIds = updatedExisting.map { it.id }.toSet()
                                val completelyNew =
                                    catalog.characters.filterNot { it.id in existingIds }
                                updatedExisting + completelyNew
                            }

                            currentState.copy(
                                characters = newList,
                                totalCount = catalog.totalCount,
                                totalPages = catalog.totalPages,
                                currentPage = catalog.currentPage,
                                isLoadingInitial = false,
                                isRetryingInitial = false,
                                isLoadingNextPage = false,
                                isRetryingPagination = false,
                                isRefreshing = false,
                                errorMessage = null
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
                                isLoadingInitial = false,
                                isRetryingInitial = false,
                                isLoadingNextPage = false,
                                isRetryingPagination = false,
                                isRefreshing = false,
                                errorMessage = uiText
                            )
                        }
                    }
                }
            }
        }
    }
}