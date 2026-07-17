package com.istudio.podnest.feature.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.istudio.podnest.core.model.Podcast
import com.istudio.podnest.core.ui.components.EmptyView
import com.istudio.podnest.core.ui.components.ErrorView
import com.istudio.podnest.core.ui.components.LoadingView
import com.istudio.podnest.core.ui.components.PodcastListRow

@Composable
fun SearchScreen(
    onPodcastClick: (Podcast) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LazyColumn(modifier = modifier, contentPadding = PaddingValues(bottom = 24.dp)) {
        item {
            Text(
                text = "Search",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(start = 20.dp, top = 18.dp, end = 20.dp, bottom = 8.dp),
            )
            OutlinedTextField(
                value = uiState.query,
                onValueChange = viewModel::onQueryChange,
                placeholder = { Text("Search podcasts, people…") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp),
            )
        }

        when {
            uiState.isLoading -> item { LoadingView(Modifier.padding(top = 40.dp)) }
            uiState.errorMessage != null -> item {
                ErrorView(message = uiState.errorMessage.orEmpty(), onRetry = {})
            }

            uiState.hasSearched && uiState.results.isEmpty() -> item {
                EmptyView(message = "No podcasts found for \"${uiState.query}\"")
            }

            !uiState.hasSearched -> item {
                EmptyView(message = "Search 4M+ podcasts via the Podcast Index API")
            }

            else -> items(uiState.results, key = { it.feedId }) { podcast ->
                PodcastListRow(podcast = podcast, onClick = onPodcastClick)
            }
        }
    }
}
