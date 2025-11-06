package com.gyg.codelab.movies.mvvm.starter.favorites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.gyg.codelab.movies.domain.model.Movie

/**
 * Implement FavoritesRoute for the Favorites feature
 *
 * This is the UI layer that connects the ViewModel to the FavoritesScreen.
 *
 * Study the HomeRoute to understand:
 * - How to collect Flow as state using collectAsStateWithLifecycle()
 * - How to provide initialValue when collecting
 * - How to pass callbacks to UI components
 *
 * What you need to do:
 * 1. Collect favoriteMovies from ViewModel (provide initialValue = emptyList())
 * 2. Pass collected state to FavoritesScreen
 * 3. Wire up callbacks to ViewModel functions
 *
 * Reference: feature-mvvm/src/main/java/com/gyg/codelab/movies/mvvm/favorites/FavoritesRoute.kt
 */
@Composable
fun FavoritesRoute(
    viewModel: FavoritesViewModel,
    onMovieClick: (Movie) -> Unit,
) {
    // TODO: Collect favoriteMovies using collectAsStateWithLifecycle()
    // Hint: Provide initialValue = emptyList()

    // TODO: Call FavoritesScreen with:
    // - favoriteMovies parameter
    // - onMovieClick callback
    // - onToggleFavorite callback that calls viewModel.toggleFavorite(it)
}
