package com.gyg.codelab.movies.mvvm.starter.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.domain.model.MovieCategory
import com.gyg.codelab.movies.ui.screens.LazyMoviesScreen
import com.gyg.codelab.movies.ui.screens.MovieSection

/**
 * Home route - displays movie categories
 * This is the entry point for the Home feature
 */
@Composable
fun HomeRoute(
    viewModel: HomeViewModel,
    onMovieClick: (Movie) -> Unit,
    onSearchClick: () -> Unit = {},
) {
    // Trigger initial load for the first visible categories
    LaunchedEffect(Unit) {
        viewModel.loadMoviesForCategory(MovieCategory.TRENDING)
        viewModel.loadMoviesForCategory(MovieCategory.POPULAR)
    }

    val movieCategories = viewModel.getAllMovieCategories()

    // Build movie sections from ViewModel state
    val movieSections = movieCategories.map { (category, flow) ->
        val isLoading by viewModel.isCategoryLoading(category).collectAsStateWithLifecycle()
        val error by viewModel.getCategoryError(category).collectAsStateWithLifecycle()

        when (category) {
            MovieCategory.TRENDING -> {
                MovieSection.Carousel(
                    category = category,
                    movies = flow,
                    error = error,
                )
            }

            else -> {
                MovieSection.Regular(
                    category = category,
                    movies = flow,
                    title = getCategoryTitle(category),
                    isLoading = isLoading,
                    error = error,
                )
            }
        }
    }

    LazyMoviesScreen(
        movieSections = movieSections,
        onMovieClick = onMovieClick,
        onSearchClick = onSearchClick,
        onToggleFavorite = { movie -> viewModel.toggleFavorite(movie) },
        onRetry = { viewModel.retryCategory() },
    )
}

/**
 * Maps MovieCategory to display title
 */
private fun getCategoryTitle(category: MovieCategory): String {
    return when (category) {
        MovieCategory.POPULAR -> "Popular Movies"
        MovieCategory.TOP_RATED -> "Top Rated"
        MovieCategory.UPCOMING -> "Upcoming Movies"
        MovieCategory.NOW_PLAYING -> "Now Playing"
        MovieCategory.TRENDING -> "Trending Now"
    }
}
