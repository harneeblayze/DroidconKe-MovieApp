package com.gyg.codelab.movies.mvi.starter.search.reducer

/**
 * TODO: Implement ToggleSearchFavoriteReducer
 *
 * This reducer handles toggling favorite status for a movie in search results.
 *
 * Pattern:
 * - Take SearchState and SearchEvent.ToggleFavorite
 * - Call favoritesRepository.toggleFavorite(movie) in a coroutine
 * - Return state unchanged (favorites update will come via reactive flow)
 *
 * Example:
 * class ToggleSearchFavoriteReducer(
 *   private val favoritesRepository: FavoritesRepository,
 *   private val scope: CoroutineScope
 * ) : Reducer<SearchState, SearchEvent.ToggleFavorite> {
 *
 *   override suspend fun reduce(
 *     state: SearchState,
 *     event: SearchEvent.ToggleFavorite
 *   ): SearchState {
 *     scope.launch {
 *       favoritesRepository.toggleFavorite(event.movie)
 *     }
 *     return state // State will update reactively via combine()
 *   }
 * }
 *
 * Reference: feature-mvi/src/main/java/com/gyg/codelab/movies/mvi/starter/home/reducer/ToggleHomeFavoriteReducer.kt
 */

// TODO: Implement ToggleSearchFavoriteReducer class here
