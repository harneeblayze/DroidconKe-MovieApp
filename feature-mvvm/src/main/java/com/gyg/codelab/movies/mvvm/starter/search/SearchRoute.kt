package com.gyg.codelab.movies.mvvm.starter.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.ui.screens.SearchScreen

/**
 * TODO: Implement SearchRoute for the Search feature
 *
 * This is the UI layer that connects the ViewModel to the SearchScreen.
 *
 * Study the HomeRoute to understand:
 * - How to collect state from ViewModel using collectAsStateWithLifecycle()
 * - How to pass state to UI components
 * - How to handle user interactions through ViewModel functions
 *
 * What you need to do:
 * 1. Collect searchResults from ViewModel
 * 2. Collect searchQuery from ViewModel
 * 3. Pass collected state to SearchScreen
 * 4. Wire up callbacks to ViewModel functions
 *
 * Reference: feature-mvvm/src/main/java/com/gyg/codelab/movies/mvvm/search/SearchRoute.kt
 */
@Composable
fun SearchRoute(
  viewModel: SearchViewModel,
  onMovieClick: (Movie) -> Unit,
) {
  // TODO: Collect searchResults and searchQuery from ViewModel

  // TODO: Pass state to SearchScreen component
  // SearchScreen needs: query, searchResults, onQueryChange, onMovieClick, onToggleFavorite
}
