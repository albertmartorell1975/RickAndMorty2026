package com.martorell.albert.rickandmorty2026.ui.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.martorell.albert.rickandmorty2026.R
import com.martorell.albert.rickandmorty2026.domain.model.Character
import com.martorell.albert.rickandmorty2026.domain.model.CharacterStatus
import com.martorell.albert.rickandmorty2026.domain.model.LocationRef
import com.martorell.albert.rickandmorty2026.ui.catalog.components.CharacterCard
import com.martorell.albert.rickandmorty2026.ui.catalog.components.CharacterPageIndicator
import com.martorell.albert.rickandmorty2026.ui.catalog.components.CharacterStatusFilterChips
import com.martorell.albert.rickandmorty2026.ui.catalog.components.InitialLoadErrorContent
import com.martorell.albert.rickandmorty2026.ui.catalog.components.PaginationErrorFooter
import com.martorell.albert.rickandmorty2026.ui.shared.RickAndMortyTopAppBar
import com.martorell.albert.rickandmorty2026.ui.theme.RickAndMorty2026Theme
import com.martorell.albert.rickandmorty2026.ui.theme.RmDevicePreview
import com.martorell.albert.rickandmorty2026.ui.theme.RmThemePreview
import com.martorell.albert.rickandmorty2026.ui.util.UiText

@Composable
fun CharacterListScreen(
    onCharacterClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    viewModel: CharacterListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val gridState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        viewModel.onStart()
    }

    val currentSize = state.characters.size
    val shouldLoadMore by remember(currentSize) {
        derivedStateOf {
            val lastVisibleItem = gridState.layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf false
            lastVisibleItem.index >= currentSize - 6
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) {
            viewModel.onLoadMore()
        }
    }

    CharacterListContent(
        state = state,
        gridState = gridState,
        onCharacterClicked = { onCharacterClicked(it.id) },
        onFavoriteToggle = viewModel::onFavoriteToggle,
        onStatusSelected = viewModel::onStatusSelected,
        onRetry = viewModel::onRetry,
        onRetryPagination = viewModel::onLoadMore,
        onRefresh = viewModel::onRefresh,
        modifier = modifier.padding(contentPadding)
    )
}

@Composable
fun CharacterListContent(
    state: CharacterListViewModel.UiState,
    gridState: LazyGridState,
    onCharacterClicked: (Character) -> Unit,
    onFavoriteToggle: (Character) -> Unit,
    onStatusSelected: (CharacterStatus?) -> Unit,
    onRetry: () -> Unit,
    onRetryPagination: () -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.characters.isEmpty() && (state.errorMessage != null || state.isRetryingInitial)) {

        InitialLoadErrorContent(
            onRetry = onRetry,
            isRetrying = state.isLoadingInitial,
            modifier = modifier
        )
    } else {

        Scaffold(
            topBar = {
                RickAndMortyTopAppBar(
                    title = stringResource(R.string.top_bar_title),
                    subtitle = stringResource(R.string.top_bar_subtitle)
                )
            },
            modifier = modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                Spacer(modifier = Modifier.height(8.dp))
                CharacterStatusFilterChips(
                    selectedStatus = state.selectedStatus,
                    onStatusSelected = onStatusSelected,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    if (state.isLoadingInitial) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    } else if (state.characters.isEmpty()) {
                        Text(
                            text = stringResource(R.string.empty_characters),
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    } else {
                        LazyVerticalGrid(
                            state = gridState,
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(bottom = 64.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(
                                items = state.characters,
                                key = { character -> character.id }
                            ) { character ->
                                CharacterCard(
                                    character = character,
                                    onCharacterClicked = onCharacterClicked,
                                    onFavoriteToggle = onFavoriteToggle
                                )
                            }
                        }

                        if (state.totalCount > 0) {
                            if (state.characters.isNotEmpty() && (state.errorMessage != null || state.isRetryingPagination)) {
                                PaginationErrorFooter(
                                    countShown = state.characters.size,
                                    totalCount = state.totalCount,
                                    onRetry = onRetryPagination,
                                    isRetrying = state.isLoadingNextPage,
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .padding(bottom = 12.dp)
                                )
                            } else {
                                CharacterPageIndicator(
                                    countShown = state.characters.size,
                                    totalCount = state.totalCount,
                                    currentPage = state.currentPage,
                                    totalPages = state.totalPages,
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .padding(bottom = 12.dp)
                                )
                            }
                        }
                    }

                    if (state.isRefreshing) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@RmThemePreview
@RmDevicePreview
@Composable
private fun CharacterListContentPreview() {
    val sampleCharacters = listOf(
        Character(
            id = 1,
            name = "Rick Sanchez",
            status = CharacterStatus.ALIVE,
            species = "Human",
            type = "",
            gender = "Male",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            origin = LocationRef("Earth (C-137)", ""),
            location = LocationRef("Citadel of Ricks", ""),
            episodeUrls = emptyList(),
            isFavorite = true
        ),
        Character(
            id = 2,
            name = "Morty Smith",
            status = CharacterStatus.DEAD,
            species = "Human",
            type = "",
            gender = "Male",
            image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
            origin = LocationRef("Earth (C-137)", ""),
            location = LocationRef("Citadel of Ricks", ""),
            episodeUrls = emptyList(),
            isFavorite = false
        )
    )

    RickAndMorty2026Theme {
        CharacterListContent(
            state = CharacterListViewModel.UiState(
                characters = sampleCharacters,
                selectedStatus = null,
                totalCount = 826,
                totalPages = 42,
                currentPage = 1,
                isLoadingInitial = false
            ),
            gridState = rememberLazyGridState(),
            onCharacterClicked = {},
            onFavoriteToggle = {},
            onStatusSelected = {},
            onRetry = {},
            onRetryPagination = {},
            onRefresh = {}
        )
    }
}

@RmThemePreview
@RmDevicePreview
@Composable
private fun CharacterListContentErrorPreview() {
    RickAndMorty2026Theme {
        CharacterListContent(
            state = CharacterListViewModel.UiState(
                characters = emptyList(),
                isLoadingInitial = false,
                errorMessage = UiText.DynamicString("No internet connection")
            ),
            gridState = rememberLazyGridState(),
            onCharacterClicked = {},
            onFavoriteToggle = {},
            onStatusSelected = {},
            onRetry = {},
            onRetryPagination = {},
            onRefresh = {}
        )
    }
}
