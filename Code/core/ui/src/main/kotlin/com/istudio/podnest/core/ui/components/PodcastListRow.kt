package com.istudio.podnest.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.istudio.podnest.core.model.Podcast
import com.istudio.podnest.core.ui.theme.PodnestAccentGradient
import com.istudio.podnest.core.ui.theme.PodnestTextFaint

/** Horizontal podcast row used in Search results and other list contexts. */
@Composable
fun PodcastListRow(
    podcast: Podcast,
    onClick: (Podcast) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(podcast) }
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(podcast.artworkUrl)
                .crossfade(250)
                .build(),
            contentDescription = podcast.title,
            modifier = Modifier
                .size(52.dp)
                .background(PodnestAccentGradient, RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop,
        )
        Column(modifier = Modifier.padding(start = 12.dp)) {
            Text(
                text = podcast.title,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
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
}
