package com.gyg.codelab.movies.mvi.starter.search

import androidx.compose.runtime.Composable
import com.gyg.codelab.movies.domain.model.Movie

/**
 * TODO: Implement SearchMviRoute for the Search feature using MVI
 *
 * This composable should:
 * - Collect searchResults and searchQuery from the ViewModel
 * - Pass them to SearchScreen component
 * - Handle user interactions (query changes, movie clicks, favorite toggles)
 *
 * Reference: feature-mvi/src/main/java/com/gyg/codelab/movies/mvi/home/HomeMviRoute.kt
 * Reference: feature-mvvm/src/main/java/com/gyg/codelab/movies/mvvm/search/SearchRoute.kt
 */
@Composable
fun SearchMviRoute(
    viewModel: SearchMviViewModel,
    onMovieClick: (Movie) -> Unit,
) {
    // TODO: Collect searchResults using collectAsStateWithLifecycle()
    // TODO: Collect searchQuery using collectAsStateWithLifecycle()

    // TODO: Call SearchScreen with:
    // - query parameter
    // - searchResults parameter
    // - onQueryChange callback that calls viewModel.searchMovies(it)
    // - onMovieClick callback
    // - onToggleFavorite callback that calls viewModel.toggleFavorite(movie)
}
