package com.martorell.albert.rickandmorty2026.ui.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.martorell.albert.rickandmorty2026.R
import com.martorell.albert.rickandmorty2026.domain.model.Character
import com.martorell.albert.rickandmorty2026.domain.model.CharacterStatus
import com.martorell.albert.rickandmorty2026.domain.model.LocationRef
import com.martorell.albert.rickandmorty2026.ui.model.toColor
import com.martorell.albert.rickandmorty2026.ui.model.toStringRes
import com.martorell.albert.rickandmorty2026.ui.theme.PortalPrimary
import com.martorell.albert.rickandmorty2026.ui.theme.RickAndMorty2026Theme
import com.martorell.albert.rickandmorty2026.ui.theme.RmDevicePreview
import com.martorell.albert.rickandmorty2026.ui.theme.RmThemePreview

@Composable
fun CharacterCard(
    character: Character,
    onCharacterClicked: (Character) -> Unit,
    onFavoriteToggle: (Character) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onCharacterClicked(character) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Image with Overlays (Floating Favorite Button & Status Badge)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                val isPreview = LocalInspectionMode.current
                val imagePlaceholder = if (isPreview) {
                    ColorPainter(MaterialTheme.colorScheme.outlineVariant)
                } else {
                    null
                }

                AsyncImage(
                    model = character.image,
                    contentDescription = character.name,
                    placeholder = imagePlaceholder,
                    error = imagePlaceholder,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 16.dp
                            )
                        )
                )

                // Favorite Button (Top Right)
                IconButton(
                    onClick = { onFavoriteToggle(character) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(32.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.5f),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = if (character.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (character.isFavorite) {
                            if (isSystemInDarkTheme()) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                PortalPrimary // Force bright green on the dark icon background
                            }
                        } else Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Status Badge (Bottom Left overlay on Image)
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.7f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(character.status.toColor(), shape = CircleShape)
                    )
                    Text(
                        text = stringResource(character.status.toStringRes()),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            }

            // Character Details below Image
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "${character.species} • ${character.origin.name}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = stringResource(R.string.character_id_format, character.id),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }
    }
}

@RmThemePreview
@RmDevicePreview
@Composable
private fun CharacterCardPreview() {
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
        episodeUrls = emptyList(),
        isFavorite = true
    )

    RickAndMorty2026Theme {
        Box(modifier = Modifier.padding(16.dp)) {
            CharacterCard(
                character = sampleCharacter,
                onCharacterClicked = {},
                onFavoriteToggle = {},
                modifier = Modifier.width(180.dp)
            )
        }
    }
}
