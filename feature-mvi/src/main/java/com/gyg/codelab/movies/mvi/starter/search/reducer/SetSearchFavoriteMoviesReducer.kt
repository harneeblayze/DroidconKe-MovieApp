package com.gyg.codelab.movies.mvi.starter.search.reducer

/**
 * TODO: Implement SetSearchFavoriteMoviesReducer
 *
 * This reducer updates the favoriteMovieIds and re-applies favorite status to search results.
 *
 * RESPONSIBILITIES:
 * - Update state.favoriteMovieIds with new IDs
 * - Call withFavoriteStatus() to apply favorites to search results
 *
 * WHAT IS withFavoriteStatus()?
 * A helper function on SearchState that re-maps searchResults
 * to update the isFavorite property of each movie based on favoriteMovieIds.
 *
 * Example in SearchState:
 * fun withFavoriteStatus(): SearchState {
 *   return copy(
 *     searchResults = searchResults.map {
 *       it.copy(isFavorite = favoriteMovieIds.contains(it.id))
 *     }
 *   )
 * }
 *
 * IMPLEMENTATION:
 * This is a SYNC reducer (pure function)
 *
 * Example:
 * class SetSearchFavoriteMoviesReducer : Reducer<SearchState, SearchEvent.SetFavoriteMovies> {
 *   override suspend fun reduce(
 *     state: SearchState,
 *     event: SearchEvent.SetFavoriteMovies
 *   ): SearchState {
 *     // TODO: Return state.copy(
 *     //   favoriteMovieIds = event.favoriteIds
 *     // ).withFavoriteStatus()
 *     //
 *     // Note: withFavoriteStatus() is called AFTER copy()
 *     // This applies the new favorite IDs to search results
 *   }
 * }
 *
 * WHY TWO STEPS?
 * 1. First, update favoriteMovieIds in state
 * 2. Then, re-apply favorites to searchResults
 *
 * REACTIVE FLOW:
 * User favorites Movie A
 *     ↓
 * ToggleFavoriteReducer updates repository
 *     ↓
 * Repository emits new favorites list
 *     ↓
 * ObserveSearchFavoritesReducer receives emission
 *     ↓
 * Publishes SetFavoriteMovies({A.id})
 *     ↓
 * THIS REDUCER updates state:
 *   - favoriteMovieIds = {A.id}
 *   - searchResults with isFavorite = true for Movie A
 *     ↓
 * UI shows filled heart for Movie A!
 *
 * EXAMPLE STATE TRANSFORMATION:
 * Before:
 * SearchState(
 *   searchResults = [
 *     Movie(id=1, title="Batman", isFavorite=false),
 *     Movie(id=2, title="Superman", isFavorite=false)
 *   ],
 *   favoriteMovieIds = {}
 * )
 *
 * Event: SetFavoriteMovies({1})
 *
 * After:
 * SearchState(
 *   searchResults = [
 *     Movie(id=1, title="Batman", isFavorite=true),   ← Updated!
 *     Movie(id=2, title="Superman", isFavorite=false)
 *   ],
 *   favoriteMovieIds = {1}
 * )
 *
 * Reference: feature-mvi/src/main/java/com/gyg/codelab/movies/mvi/search/reducer/SetSearchFavoriteMoviesReducer.kt
 */

// TODO: Implement SetSearchFavoriteMoviesReducer class here
