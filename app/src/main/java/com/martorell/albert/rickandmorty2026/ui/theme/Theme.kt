package com.martorell.albert.rickandmorty2026.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val CitadelPortalDarkColorScheme = darkColorScheme(
    primary = PortalPrimary,
    onPrimary = PortalOnPrimary,
    primaryContainer = PortalPrimaryContainer,
    onPrimaryContainer = PortalOnPrimaryContainer,
    secondary = PortalSecondary,
    onSecondary = PortalOnSecondary,
    secondaryContainer = PortalSecondaryContainer,
    onSecondaryContainer = PortalOnSecondaryContainer,
    tertiary = PortalTertiary,
    onTertiary = PortalOnTertiary,
    tertiaryContainer = PortalTertiaryContainer,
    onTertiaryContainer = PortalOnTertiaryContainer,
    background = PortalBackground,
    onBackground = PortalOnBackground,
    surface = PortalSurface,
    onSurface = PortalOnSurface,
    surfaceVariant = PortalSurfaceVariant,
    onSurfaceVariant = PortalOnSurfaceVariant,
    error = PortalError,
    onError = PortalOnError,
    errorContainer = PortalErrorContainer,
    onErrorContainer = PortalOnErrorContainer
)

private val CitadelPortalLightColorScheme = lightColorScheme(
    primary = Color(0xFF355500), // Darker green for text readability on light backgrounds
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFB2EB65),
    onPrimaryContainer = Color(0xFF112000),
    secondary = Color(0xFF006877),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFA4EEFF),
    onSecondaryContainer = Color(0xFF001F25),
    tertiary = Color(0xFF006C3B),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFF56FFA7),
    onTertiaryContainer = Color(0xFF002110),
    background = Color(0xFFF6F8FC),
    onBackground = Color(0xFF171B27),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF171B27),
    // 💡 Fix: Lighter surfaceVariant for Light Mode cards to contrast with background
    surfaceVariant = Color(0xFFF0F4E8), 
    onSurfaceVariant = Color(0xFF434938),
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002)
)

@Composable
fun RickAndMorty2026Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color disabled by default to preserve Citadel Portal thematic identity
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> CitadelPortalDarkColorScheme
        else -> CitadelPortalLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
