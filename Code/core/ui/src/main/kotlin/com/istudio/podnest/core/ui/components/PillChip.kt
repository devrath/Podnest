package com.istudio.podnest.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.istudio.podnest.core.ui.theme.PodnestAccentGradient
import com.istudio.podnest.core.ui.theme.PodnestBorder
import com.istudio.podnest.core.ui.theme.PodnestSurfaceVariant
import com.istudio.podnest.core.ui.theme.PodnestTextDim

/** Filter/category chip used for chip rows (Discover categories, Search filters). */
@Composable
fun PillChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundModifier = if (selected) {
        Modifier.background(PodnestAccentGradient, CircleShape)
    } else {
        Modifier
            .background(PodnestSurfaceVariant, CircleShape)
            .border(1.dp, PodnestBorder, CircleShape)
    }

    Text(
        text = label,
        style = MaterialTheme.typography.labelLarge,
        color = if (selected) Color.White else PodnestTextDim,
        modifier = modifier
            .then(backgroundModifier)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 7.dp),
    )
}
