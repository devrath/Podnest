package com.istudio.podnest.core.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Podnest brand palette — dark-first, gradient accent (purple -> blue -> teal),
// matching the wireframe prototype (Podnest_Wireframe_Prototype.html).

val PodnestBackground = Color(0xFF0A0C12)
val PodnestSurface = Color(0xFF12151F)
val PodnestSurfaceVariant = Color(0xFF1A1E2B)
val PodnestSurfaceHigh = Color(0xFF232838)
val PodnestBorder = Color(0xFF262B3A)

val PodnestTextPrimary = Color(0xFFF2F3F7)
val PodnestTextDim = Color(0xFF9AA1B4)
val PodnestTextFaint = Color(0xFF5F6579)

val PodnestAccentPurple = Color(0xFF8B6BFF)
val PodnestAccentBlue = Color(0xFF5B8CFF)
val PodnestAccentTeal = Color(0xFF22D3EE)

val PodnestSuccess = Color(0xFF3DDC97)
val PodnestWarning = Color(0xFFFFB454)
val PodnestDanger = Color(0xFFFF6B6B)

/** The signature Podnest gradient — used on primary CTAs, active nav icons, mini-player accents. */
val PodnestAccentGradient = Brush.linearGradient(
    colors = listOf(PodnestAccentPurple, PodnestAccentBlue, PodnestAccentTeal),
)

// Light-theme fallback (Podnest is dark-first, but Material3 requires a light scheme too).
val PodnestLightBackground = Color(0xFFFAFAFC)
val PodnestLightSurface = Color(0xFFFFFFFF)
