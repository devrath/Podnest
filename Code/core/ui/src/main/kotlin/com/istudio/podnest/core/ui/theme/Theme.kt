package com.istudio.podnest.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val PodnestDarkColorScheme = darkColorScheme(
    primary = PodnestAccentPurple,
    onPrimary = PodnestTextPrimary,
    secondary = PodnestAccentTeal,
    onSecondary = PodnestBackground,
    tertiary = PodnestAccentBlue,
    background = PodnestBackground,
    onBackground = PodnestTextPrimary,
    surface = PodnestSurface,
    onSurface = PodnestTextPrimary,
    surfaceVariant = PodnestSurfaceVariant,
    onSurfaceVariant = PodnestTextDim,
    outline = PodnestBorder,
    error = PodnestDanger,
)

private val PodnestLightColorScheme = lightColorScheme(
    primary = PodnestAccentPurple,
    secondary = PodnestAccentTeal,
    tertiary = PodnestAccentBlue,
    background = PodnestLightBackground,
    surface = PodnestLightSurface,
    error = PodnestDanger,
)

/**
 * Podnest is dark-first by design (matches the wireframe prototype), but a
 * light scheme is provided for system-theme compliance. No dynamic color —
 * the brand gradient is a deliberate, consistent identity across devices.
 */
@Composable
fun PodnestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) PodnestDarkColorScheme else PodnestLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = PodnestTypography,
        shapes = PodnestShapes,
        content = content,
    )
}
