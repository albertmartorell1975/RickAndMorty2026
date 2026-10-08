package com.martorell.albert.rickandmorty2026.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.martorell.albert.rickandmorty2026.R
import com.martorell.albert.rickandmorty2026.domain.model.Character
import com.martorell.albert.rickandmorty2026.domain.model.CharacterStatus
import com.martorell.albert.rickandmorty2026.domain.model.Episode
import com.martorell.albert.rickandmorty2026.domain.model.LocationRef
import com.martorell.albert.rickandmorty2026.ui.detail.components.EpisodesCard
import com.martorell.albert.rickandmorty2026.ui.detail.components.HeroIdentitySection
import com.martorell.albert.rickandmorty2026.ui.detail.components.SpacetimeCard
import com.martorell.albert.rickandmorty2026.ui.list.components.InitialLoadErrorContent
import com.martorell.albert.rickandmorty2026.ui.theme.RickAndMorty2026Theme
import com.martorell.albert.rickandmorty2026.ui.theme.RmDevicePreview
import com.martorell.albert.rickandmorty2026.ui.theme.RmThemePreview

@Composable
fun CharacterDetailScreen(
    snackbarHostState: SnackbarHostState,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onStart()
    }

    val toastMessage = state.toastMessage?.asString()
    LaunchedEffect(toastMessage) {
        if (toastMessage != null) {
            snackbarHostState.showSnackbar(toastMessage)
            viewModel.onToastDismissed()
        }
    }

    CharacterDetailContent(
        state = state,
        onBackClicked = onBackClicked,
        onFavoriteToggle = viewModel::onFavoriteToggle,
        onRetry = viewModel::onRetry,
        modifier = modifier.padding(contentPadding)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailContent(
    state: CharacterDetailViewModel.UiState,
    onBackClicked: () -> Unit,
    onFavoriteToggle: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.top_bar_title),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClicked) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                state.errorMessage != null && state.character == null -> {
                    InitialLoadErrorContent(
                        onRetry = onRetry,
                        isRetrying = state.isLoading,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                state.character != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        HeroIdentitySection(character = state.character)
                        SpacetimeCard(character = state.character)
                        EpisodesCard(
                            episodes = state.episodes,
                            totalCount = state.character.episodeUrls.size
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }

        if (state.character != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                val isFavorite = state.character.isFavorite
                Button(
                    onClick = onFavoriteToggle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isFavorite) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant
                        },
                        contentColor = if (isFavorite) {
                            MaterialTheme.colorScheme.onPrimary
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        }
                    )
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isFavorite) "Remove from Favorites" else "Add to Favorites",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}


@RmThemePreview
@RmDevicePreview
@Composable
private fun CharacterDetailContentPreview() {
    val sampleCharacter = Character(
        id = 1,
        name = "Rick Sanchez",
        status = CharacterStatus.ALIVE,
        species = "Human",
        type = "",
        gender = "Male",
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        origin = LocationRef("Earth (C-137)", ""),
        location = LocationRef("Citadel of Ricks", ""),
        episodeUrls = listOf("1", "2", "3"),
        isFavorite = true
    )

    val sampleEpisodes = listOf(
        Episode(1, "Pilot", "Dec 2, 2013", "S01E01", emptyList()),
        Episode(2, "Lawnmower Dog", "Dec 9, 2013", "S01E02", emptyList()),
        Episode(3, "Anatomy Park", "Dec 16, 2013", "S01E03", emptyList())
    )

    RickAndMorty2026Theme {
        CharacterDetailContent(
            state = CharacterDetailViewModel.UiState(
                isLoading = false,
                character = sampleCharacter,
                episodes = sampleEpisodes
            ),
            onBackClicked = {},
            onFavoriteToggle = {},
            onRetry = {}
        )
    }
}
