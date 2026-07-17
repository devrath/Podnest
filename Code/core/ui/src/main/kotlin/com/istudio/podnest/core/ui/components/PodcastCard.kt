package com.istudio.podnest.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.istudio.podnest.core.model.Podcast
import com.istudio.podnest.core.ui.theme.PodnestAccentGradient
import com.istudio.podnest.core.ui.theme.PodnestTextFaint

/** Vertical artwork tile used in horizontally-scrolling rails (Discover/Search). */
@Composable
fun PodcastCard(
    podcast: Podcast,
    onClick: (Podcast) -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 120.dp,
) {
    Column(
        modifier = modifier
            .width(size)
            .clickable { onClick(podcast) },
    ) {
        AsyncImage(
            model = podcast.artworkUrl,
            contentDescription = podcast.title,
            modifier = Modifier
                .width(size)
                .background(PodnestAccentGradient, RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop,
        )
        Text(
            text = podcast.title,
            style = MaterialTheme.typography.titleSmall,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 8.dp),
        )
        Text(
            text = podcast.author,
            style = MaterialTheme.typography.bodySmall,
            color = PodnestTextFaint,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
