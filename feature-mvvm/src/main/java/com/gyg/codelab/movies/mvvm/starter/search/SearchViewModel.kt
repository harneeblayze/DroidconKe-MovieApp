package com.gyg.codelab.movies.mvvm.starter.search

import androidx.lifecycle.ViewModel
import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.domain.repository.SearchRepository
import com.gyg.codelab.movies.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * TODO: Implement SearchViewModel for the Search screen
 *
 * Requirements:
 * - Handle search queries from user input
 * - Manage search results with reactive favorite updates
 * - Combine search results with favorites repository to show correct favorite status
 *
 * Study the HomeViewModel to understand:
 * - How to use combine() operator for reactive updates
 * - How to manage StateFlow for UI state
 * - How to handle repository calls in viewModelScope
 *
 * What you need to implement:
 * 1. A way to store raw search results
 * 2. Expose search results as StateFlow with favorite status applied
 * 3. Expose current search query as StateFlow
 * 4. Function to perform search with a query string
 * 5. Function to toggle favorite status
 *
 * Reference: feature-mvvm/src/main/java/com/gyg/codelab/movies/mvvm/search/SearchViewModel.kt
 */
class SearchViewModel(
  private val searchRepository: SearchRepository,
  private val favoritesRepository: FavoritesRepository
) : ViewModel() {

  // TODO: Implement raw search results state
  private val _rawSearchResults = MutableStateFlow<List<Movie>>(emptyList())

  // TODO: Implement search results with reactive favorite status
  // Hint: Use combine() with _rawSearchResults and favoritesRepository.getFavoriteMovies()
  val searchResults: StateFlow<List<Movie>> = _rawSearchResults.asStateFlow()

  // TODO: Implement search query state
  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

  /**
   * TODO: Implement search functionality
   * What to do:
   * - Update the search query state
   * - Handle empty query case (clear results)
   * - Call searchRepository.searchMovies() with the query
   * - Update results state on success
   */
  fun searchMovies(query: String) {
    // TODO: Implement
  }

  /**
   * TODO: Implement toggle favorite functionality
   * What to do:
   * - Call favoritesRepository.toggleFavorite() in a coroutine
   * - Remember: combine() will automatically update the UI
   */
  fun toggleFavorite(movie: Movie) {
    // TODO: Implement
  }
}
