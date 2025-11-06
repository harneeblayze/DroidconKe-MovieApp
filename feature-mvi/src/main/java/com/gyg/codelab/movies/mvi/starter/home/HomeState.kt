package com.gyg.codelab.movies.mvi.starter.home

import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.domain.model.MovieCategory
import com.gyg.codelab.movies.mvi.state_manager.State
import com.gyg.codelab.movies.ui.screens.MovieSection

/**
 * Represents the loading state of a movie category
 * Each category can independently be in Loading, Success, or Error state
 */
sealed class CategoryState {
    data object Loading : CategoryState()
    data class Success(val movies: List<Movie>) : CategoryState()
    data class Error(val message: String) : CategoryState()

    /**
     * Helper to get movies if state is Success, empty list otherwise
     */
    fun moviesOrEmpty(): List<Movie> = when (this) {
        is Success -> movies
        else -> emptyList()
    }

    /**
     * Helper to check if category is loading
     */
    fun isLoading(): Boolean = this is Loading

    /**
     * Helper to get error message if state is Error
     */
    fun errorOrNull(): String? = when (this) {
        is Error -> message
        else -> null
    }
}

/**
 * Domain state for Home feature
 * Simple, flat structure with map-based movie categories
 * Favorites are applied reactively in load reducers by combining flows
 */
data class HomeState(
    val movies: Map<MovieCategory, CategoryState> = emptyMap(),
) : State {

    companion object {
        fun initial() = HomeState()
    }
}

/**
 * UI state for Home feature
 * Transformed from HomeState for UI consumption
 * Contains MovieSections ready for display in LazyMoviesScreen
 */
data class HomeUIState(
    val movieSections: List<MovieSection> = emptyList(),
) {
    val isEmpty: Boolean
        get() = movieSections.all { section ->
            when (section) {
                is MovieSection.Carousel -> false // Carousel presence counts as not empty
                is MovieSection.Regular -> section.isLoading || section.error != null
            }
        }

    companion object {
        fun initial() = HomeUIState()
    }
}
