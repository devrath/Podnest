package com.istudio.podnest.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.istudio.podnest.feature.discover.navigation.DISCOVER_ROUTE
import com.istudio.podnest.feature.library.navigation.LIBRARY_ROUTE
import com.istudio.podnest.feature.search.navigation.SEARCH_ROUTE

/** The three top-level, always-visible bottom nav destinations. */
enum class PodnestDestination(
    val route: String,
    val label: String,
    val icon: ImageVector,
) {
    DISCOVER(DISCOVER_ROUTE, "Discover", Icons.Filled.Home),
    SEARCH(SEARCH_ROUTE, "Search", Icons.Filled.Search),
    LIBRARY(LIBRARY_ROUTE, "Library", Icons.Filled.LibraryMusic),
}
