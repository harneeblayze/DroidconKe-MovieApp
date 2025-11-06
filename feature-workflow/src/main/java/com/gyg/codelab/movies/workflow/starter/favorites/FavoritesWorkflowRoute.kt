package com.gyg.codelab.movies.workflow.starter.favorites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.gyg.codelab.movies.domain.model.Movie

/**
 * TODO: Implement FavoritesWorkflowRoute for the Favorites feature using Workflow
 *
 * This composable should:
 * - Collect favoritesRendering from the ViewModel
 * - Extract favorites and onToggleFavourite from rendering
 * - Pass them to FavoritesScreen component
 *
 * Note: You need to:
 * 1. First implement FavoritesWorkflow in FavoritesWorkflow.kt
 * 2. Then implement FavoritesWorkflowViewModel
 * 3. Then uncomment the code below
 *
 * Pattern (based on actual solution):
 * @Composable
 * fun FavoritesWorkflowRoute(
 *   viewModel: FavoritesWorkflowViewModel,
 *   onMovieClick: (Movie) -> Unit,
 * ) {
 *   val rendering by viewModel.favoritesRendering.collectAsStateWithLifecycle()
 *
 *   FavoritesScreen(
 *     favoriteMovies = rendering.favorites,
 *     onMovieClick = onMovieClick,
 *     onToggleFavorite = rendering.onToggleFavourite
 *   )
 * }
 *
 * Note: Unlike MVVM/MVI, we collect the rendering object which contains
 * both the data (favorites) and the event handler (onToggleFavourite).
 *
 * Reference: feature-workflow/src/main/java/com/gyg/codelab/movies/workflow/favorites/FavoritesWorkflowRoute.kt
 */
@Composable
fun FavoritesWorkflowRoute(
    viewModel: FavoritesWorkflowViewModel,
    onMovieClick: (Movie) -> Unit,
) {
    // TODO: Once FavoritesWorkflow and FavoritesWorkflowViewModel are implemented, uncomment below:

    // val rendering by viewModel.favoritesRendering.collectAsStateWithLifecycle()

    // FavoritesScreen(
    //   favoriteMovies = rendering.favorites,
    //   onMovieClick = onMovieClick,
    //   onToggleFavorite = rendering.onToggleFavourite
    // )
}
