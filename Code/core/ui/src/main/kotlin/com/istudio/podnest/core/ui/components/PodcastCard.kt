package com.istudio.podnest.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.istudio.podnest.core.model.Podcast
import com.istudio.podnest.core.ui.theme.PodnestAccentGradient
import com.istudio.podnest.core.ui.theme.PodnestTextFaint

/**
 * Vertical artwork tile used in horizontally-scrolling rails (Discover/Search)
 * and the Library grid.
 *
 * The artwork is pinned to a 1:1 [aspectRatio] *before* the image loads, so
 * every card reserves the same box from the very first frame — without this,
 * Coil renders a zero-height image until the bitmap arrives, and since cards
 * in a LazyRow finish loading at different times, the whole rail visibly
 * jumps/reflows as you scroll. `minLines = 2` on the title does the same job
 * for text, so every card is the same height regardless of title length.
 */
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
            model = ImageRequest.Builder(LocalContext.current)
                .data(podcast.artworkUrl)
                .crossfade(250)
                .build(),
            contentDescription = podcast.title,
            modifier = Modifier
                .width(size)
                .aspectRatio(1f)
                .background(PodnestAccentGradient, RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop,
        )
        Text(
            text = podcast.title,
            style = MaterialTheme.typography.titleSmall,
            maxLines = 2,
            minLines = 2,
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
