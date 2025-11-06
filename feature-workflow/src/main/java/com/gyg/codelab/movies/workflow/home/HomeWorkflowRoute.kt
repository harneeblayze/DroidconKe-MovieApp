package com.gyg.codelab.movies.workflow.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.ui.screens.LazyMoviesScreen
import org.koin.androidx.compose.koinViewModel

/**
 * Workflow Home Route
 *
 * Entry point for the home feature using Square's Workflow architecture.
 * This uses the common LazyMoviesScreen UI by adapting the workflow state machine
 * through HomeWorkflowViewModel.
 *
 * Note: Favorites are handled by MovieCategoryWorkflow's toggleFavourite event handler.
 * The workflow manages favorites reactively through combined flows, so changes
 * automatically propagate to all movie lists.
 */
@Composable
fun HomeWorkflowRoute(
    viewModel: HomeWorkflowViewModel = koinViewModel(),
    onMovieClick: (Movie) -> Unit,
    onSearchClick: () -> Unit = {},
) {
    val rendering by viewModel.homeRendering.collectAsStateWithLifecycle()

    LazyMoviesScreen(
        movieSections = rendering.movies,
        onMovieClick = onMovieClick,
        onSearchClick = onSearchClick,
        onToggleFavorite = rendering.onToggleFavourite,
        onRetry = {
            rendering.onRetryLoad()
        },
    )
}
