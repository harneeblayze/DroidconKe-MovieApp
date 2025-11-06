package com.gyg.codelab.movies.mvi.starter.favorites

import androidx.compose.runtime.Composable
import com.gyg.codelab.movies.domain.model.Movie

/**
 * TODO: Implement FavoritesMviRoute for the Favorites feature using MVI
 *
 * This composable should:
 * - Collect favoriteMovies from the ViewModel
 * - Pass them to FavoritesScreen component
 * - Handle user interactions (movie clicks, favorite toggles)
 *
 * Reference: feature-mvi/src/main/java/com/gyg/codelab/movies/mvi/favorites/FavoritesMviRoute.kt
 * Reference: feature-mvvm/src/main/java/com/gyg/codelab/movies/mvvm/favorites/FavoritesRoute.kt
 */
@Composable
fun FavoritesMviRoute(
    viewModel: FavoritesMviViewModel,
    onMovieClick: (Movie) -> Unit,
) {
    // TODO: Collect favoriteMovies using collectAsStateWithLifecycle()
    // Hint: Provide initialValue = emptyList()

    // TODO: Call FavoritesScreen with:
    // - favoriteMovies parameter
    // - onMovieClick callback
    // - onToggleFavorite callback that calls viewModel.toggleFavorite(it)
}
