package com.gyg.codelab.movies.mvi.starter.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.ui.screens.LazyMoviesScreen

/**
 * MVI Home Route
 *
 * Entry point for the home feature using MVI architecture.
 * Connects the HomeMviViewModel to LazyMoviesScreen.
 */
@Composable
fun HomeMviRoute(
  viewModel: HomeMviViewModel,
  onMovieClick: (Movie) -> Unit,
  onSearchClick: () -> Unit = {},
) {
  // Collect UI state with movieSections
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  LazyMoviesScreen(
    movieSections = uiState.movieSections,
    onMovieClick = onMovieClick,
    onSearchClick = onSearchClick,
    onToggleFavorite = { movie ->
      viewModel.toggleFavorite(movie)
    },
    onRetry = { category ->
      viewModel.handleEvent(HomeEvent.Refresh)
    }
  )
}
