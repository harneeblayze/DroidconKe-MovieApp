package com.gyg.codelab.movies.mvvm.starter.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gyg.codelab.movies.domain.exceptions.MovieException
import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.domain.model.MovieCategory
import com.gyg.codelab.movies.domain.repository.FavoritesRepository
import com.gyg.codelab.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * ViewModel for the Home screen
 * Responsibilities:
 * - Loading movies by category
 * - Managing loading and error states per category
 * - Managing favorite movies with reactive updates
 * - Handling retry logic
 */
class HomeViewModel(
    private val moviesRepository: MoviesRepository,
    private val favoritesRepository: FavoritesRepository,
) : ViewModel() {

    // UI State using a map for all movie categories
    private val movieStateFlows = mutableMapOf<MovieCategory, MutableStateFlow<List<Movie>>>()
    private val loadedCategories = mutableSetOf<MovieCategory>()

    // Track which categories have been triggered for loading
    private val triggeredCategories = mutableSetOf<MovieCategory>()

    // Loading states - per category
    private val categoryLoadingStates = mutableMapOf<MovieCategory, MutableStateFlow<Boolean>>()

    // Error states - per category
    private val categoryErrorStates = mutableMapOf<MovieCategory, MutableStateFlow<String?>>()

    private val categoryFlows = listOf(
        MovieCategory.TRENDING to getOrCreateStateFlow(MovieCategory.TRENDING),
        MovieCategory.POPULAR to getOrCreateStateFlow(MovieCategory.POPULAR),
        MovieCategory.TOP_RATED to getOrCreateStateFlow(MovieCategory.TOP_RATED),
        MovieCategory.UPCOMING to getOrCreateStateFlow(MovieCategory.UPCOMING),
        MovieCategory.NOW_PLAYING to getOrCreateStateFlow(MovieCategory.NOW_PLAYING),
    )

    /**
     * Gets or creates a StateFlow for a specific movie category
     * Uses combine() to automatically update when favorites change
     * This is fully reactive - no manual refresh needed!
     */
    private fun getOrCreateStateFlow(category: MovieCategory): Flow<List<Movie>> {
        val baseFlow = movieStateFlows.getOrPut(category) {
            MutableStateFlow(emptyList())
        }

        // Combine movies with favorites - automatically updates when either changes
        return combine(
            baseFlow,
            favoritesRepository.getFavoriteMovies(),
        ) { movies, favorites ->
            val favoriteIds = favorites.map { it.id }.toSet()
            movies.map { movie ->
                movie.copy(isFavorite = favoriteIds.contains(movie.id))
            }
        }.onStart {
            loadMoviesForCategory(category)
        }
    }

    /**
     * Gets or creates a loading state flow for a specific category
     */
    private fun getOrCreateLoadingStateFlow(category: MovieCategory): MutableStateFlow<Boolean> {
        return categoryLoadingStates.getOrPut(category) {
            MutableStateFlow(false)
        }
    }

    /**
     * Gets or creates an error state flow for a specific category
     */
    private fun getOrCreateErrorStateFlow(category: MovieCategory): MutableStateFlow<String?> {
        return categoryErrorStates.getOrPut(category) {
            MutableStateFlow(null)
        }
    }

    /**
     * Loads movies for a specific category if not already loaded
     * This enables lazy loading - only fetch when needed
     */
    fun loadMoviesForCategory(category: MovieCategory, forceRefresh: Boolean = false) {
        // Skip if already loaded and not forcing refresh
        if (!forceRefresh && loadedCategories.contains(category)) {
            return
        }

        // Skip if already triggered (prevents multiple simultaneous loads)
        if (!forceRefresh && triggeredCategories.contains(category)) {
            return
        }

        triggeredCategories.add(category)

        viewModelScope.launch {
            val loadingState = getOrCreateLoadingStateFlow(category)
            val errorState = getOrCreateErrorStateFlow(category)
            val movieState = movieStateFlows.getOrPut(category) { MutableStateFlow(emptyList()) }

            loadingState.value = true
            errorState.value = null // Clear previous errors

            // Add a small delay to show loading state (for demo purposes)
            delay(500)

            moviesRepository.getMovies(category)
                .onSuccess { movies ->
                    // Just set the raw movies - combine() will handle adding favorite status
                    movieState.value = movies
                    loadedCategories.add(category)
                    errorState.value = null
                }
                .onFailure { exception ->
                    val errorMessage = getErrorMessage(exception)
                    errorState.value = errorMessage
                }

            loadingState.value = false
        }
    }

    /**
     * Toggle favorite status of a movie
     * No manual refresh needed - combine() automatically updates all movie lists!
     */
    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            favoritesRepository.toggleFavorite(movie)
            //  The combine operator in getOrCreateStateFlow()
            // will automatically emit updated movies with new favorite status
        }
    }

    /**
     * Maps exceptions to user-friendly error messages
     */
    private fun getErrorMessage(throwable: Throwable): String {
        return when (throwable) {
            is MovieException.NetworkError -> "No internet connection. Please check your network."
            is MovieException.TimeoutError -> "Request timed out. Please try again."
            is MovieException.ServerError -> "Server is not responding. Please try again later."
            is MovieException.ClientError -> when (throwable.code) {
                401 -> "Authentication failed"
                403 -> "Access denied"
                404 -> "Content not found"
                else -> "Request failed"
            }

            is MovieException.DataError -> "Unable to load data"
            is MovieException.UnknownError -> "Something went wrong. Please try again."
            else -> "An unexpected error occurred"
        }
    }

    /**
     * Check if a category is currently loading
     */
    fun isCategoryLoading(category: MovieCategory): StateFlow<Boolean> {
        return getOrCreateLoadingStateFlow(category).asStateFlow()
    }

    /**
     * Get all movie categories as a list
     * Useful for UI to iterate over
     */
    fun getAllMovieCategories(): List<Pair<MovieCategory, Flow<List<Movie>>>> {
        return categoryFlows
    }

    /**
     * Check if a category has an error
     */
    fun getCategoryError(category: MovieCategory): StateFlow<String?> {
        return getOrCreateErrorStateFlow(category).asStateFlow()
    }

    /**
     * Retry loading a category that failed
     */
    fun retryCategory() {
        // Clear the error and triggered state to allow retry
        categoryErrorStates.forEach { (category, errorFlow) ->
            if (errorFlow.value != null) {
                errorFlow.value = null
                triggeredCategories.remove(category)
                loadedCategories.remove(category)
                loadMoviesForCategory(category, forceRefresh = true)
            }
        }
    }
}
