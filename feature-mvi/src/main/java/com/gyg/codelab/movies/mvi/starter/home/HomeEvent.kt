package com.gyg.codelab.movies.mvi.starter.home

import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.domain.model.MovieCategory
import com.gyg.codelab.movies.mvi.state_manager.Event

/**
 * Events for Home feature
 * Represents user actions and system events for the home screen
 */
sealed class HomeEvent : Event {
    // Load events - now generic with category
    object LoadAllMovies : HomeEvent()
    data class LoadMovieCategory(val category: MovieCategory) : HomeEvent()

    // Data setting events - now generic with category
    data class SetCategoryMovies(val category: MovieCategory, val movies: List<Movie>) : HomeEvent()
    data class SetCategoryError(val category: MovieCategory, val message: String) : HomeEvent()

    // Favorites events
    data class ToggleFavorite(val movie: Movie) : HomeEvent()

    // Refresh event
    object Refresh : HomeEvent()
}
