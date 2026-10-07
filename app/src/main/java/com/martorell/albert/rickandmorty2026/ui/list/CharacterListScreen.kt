package com.martorell.albert.rickandmorty2026.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.martorell.albert.rickandmorty2026.ui.list.components.CharacterCard
import com.martorell.albert.rickandmorty2026.ui.list.components.CharacterSearchBar
import com.martorell.albert.rickandmorty2026.ui.list.components.CharacterStatusFilterChips
import com.martorell.albert.rickandmorty2026.ui.theme.RickAndMorty2026Theme
import com.martorell.albert.rickandmorty2026.ui.theme.RmDevicePreview
import com.martorell.albert.rickandmorty2026.ui.theme.RmThemePreview

@Composable
fun CharacterListScreen(
    onCharacterClicked: (Character) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CharacterListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onStart()
    }

    CharacterListContent(
        state = state,
        onCharacterClicked = onCharacterClicked,
        onFavoriteToggle = viewModel::onFavoriteToggle,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onStatusSelected = viewModel::onStatusSelected,
        onRetry = viewModel::onStart,
        onRefresh = viewModel::onRefresh,
        modifier = modifier
    )
}

@Composable
fun CharacterListContent(
    state: CharacterListViewModel.UiState,
    onCharacterClicked: (Character) -> Unit,
    onFavoriteToggle: (Character) -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onStatusSelected: (CharacterStatus?) -> Unit,
    onRetry: () -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
    ) {
        CharacterSearchBar(
            query = state.searchQuery,
            onQueryChanged = onSearchQueryChanged
        )

        Spacer(modifier = Modifier.height(8.dp))

        CharacterStatusFilterChips(
            selectedStatus = state.selectedStatus,
            onStatusSelected = onStatusSelected
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(modifier = Modifier.fillMaxSize()) {
            when (val content = state.content) {
                is CharacterListViewModel.CharacterListContent.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is CharacterListViewModel.CharacterListContent.Error -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = content.message,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Button(onClick = onRetry) {
                            Text(text = stringResource(R.string.error_retry))
                        }
                    }
                }

                is CharacterListViewModel.CharacterListContent.Success -> {
                    if (content.characters.isEmpty()) {
                        Text(
                            text = stringResource(R.string.empty_characters),
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(bottom = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(
                                items = content.characters,
                                key = { character -> character.id }
                            ) { character ->
                                CharacterCard(
                                    character = character,
                                    onCharacterClicked = onCharacterClicked,
                                    onFavoriteToggle = onFavoriteToggle
                                )
                            }
                        }
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
                content = CharacterListViewModel.CharacterListContent.Success(sampleCharacters),
                searchQuery = "",
                selectedStatus = null
            ),
            onCharacterClicked = {},
            onFavoriteToggle = {},
            onSearchQueryChanged = {},
            onStatusSelected = {},
            onRetry = {},
            onRefresh = {}
        )
    }
}
