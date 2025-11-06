package com.gyg.codelab.movies.mvvm.starter.favorites

import androidx.lifecycle.ViewModel
import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * TODO: Implement FavoritesViewModel for the Favorites screen
 *
 * Requirements:
 * - Display all favorited movies
 * - Allow users to toggle favorite status
 * - Keep UI updated automatically when favorites change
 *
 * This is the simplest ViewModel - the repository already provides a Flow!
 *
 * Study the HomeViewModel to understand:
 * - How to expose Flow from repository
 * - How to handle user actions in viewModelScope
 *
 * What you need to implement:
 * 1. Expose favorite movies as a Flow
 * 2. Function to toggle favorite status
 *
 * Reference: feature-mvvm/src/main/java/com/gyg/codelab/movies/mvvm/favorites/FavoritesViewModel.kt
 */
class FavoritesViewModel(
    private val favoritesRepository: FavoritesRepository,
) : ViewModel() {

    // TODO: Expose favorite movies flow from repository
    val favoriteMovies: Flow<List<Movie>> = flowOf(emptyList())

    /**
     * TODO: Implement toggle favorite functionality
     * What to do:
     * - Launch a coroutine in viewModelScope
     * - Call favoritesRepository.toggleFavorite()
     */
    fun toggleFavorite(movie: Movie) {
        // TODO: Implement
    }
}
