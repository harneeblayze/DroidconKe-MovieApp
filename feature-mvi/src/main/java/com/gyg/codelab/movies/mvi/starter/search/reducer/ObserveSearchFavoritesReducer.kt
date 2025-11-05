package com.gyg.codelab.movies.mvi.starter.search.reducer

/**
 * TODO: Implement ObserveSearchFavoritesReducer
 *
 * This reducer observes the favorites repository and publishes events when favorites change.
 *
 * RESPONSIBILITIES:
 * - Observe favoritesRepository.getFavoriteMovies() Flow
 * - Extract favorite movie IDs from the list
 * - Publish SetFavoriteMovies event with the IDs
 *
 * WHY OBSERVE FAVORITES?
 * When user toggles a favorite in search results, we need to update the
 * isFavorite status of all movies in the search results.
 *
 * REACTIVE PATTERN:
 * favoritesRepository.getFavoriteMovies() → Flow<List<Movie>>
 *     ↓
 * Extract IDs → Set<Int>
 *     ↓
 * Publish SetFavoriteMovies(favoriteIds)
 *     ↓
 * SetSearchFavoriteMoviesReducer updates state
 *     ↓
 * UI shows updated favorite status
 *
 * IMPLEMENTATION:
 * This is an ASYNC reducer (launches a coroutine)
 *
 * Example:
 * class ObserveSearchFavoritesReducer(
 *   private val favoritesRepository: FavoritesRepository,
 *   private val scope: CoroutineScope,
 *   private val publishEvent: (SearchEvent) -> Unit
 * ) : Reducer<SearchState, SearchEvent.ObserveFavorites> {
 *
 *   override suspend fun reduce(
 *     state: SearchState,
 *     event: SearchEvent.ObserveFavorites
 *   ): SearchState {
 *     // TODO: Implement
 *     //
 *     // scope.launch {
 *     //   favoritesRepository.getFavoriteMovies()
 *     //     .collect { favorites ->
 *     //       val favoriteIds = favorites.map { it.id }.toSet()
 *     //       publishEvent(SearchEvent.SetFavoriteMovies(favoriteIds))
 *     //     }
 *     // }
 *     //
 *     // return state  // State unchanged (async operation)
 *   }
 * }
 *
 * WHEN IS THIS TRIGGERED?
 * - When SearchMviViewModel starts (in onStart or init)
 * - This sets up continuous observation of favorites
 *
 * FLOW:
 * User favorites Movie A in search
 *     ↓
 * ToggleFavoriteReducer updates repository
 *     ↓
 * favoritesRepository.getFavoriteMovies() emits new list
 *     ↓
 * ObserveSearchFavoritesReducer receives emission
 *     ↓
 * Publishes SetFavoriteMovies([A's ID])
 *     ↓
 * SetSearchFavoriteMoviesReducer updates state.favoriteMovieIds
 *     ↓
 * withFavoriteStatus() re-applies to search results
 *     ↓
 * UI shows Movie A with filled heart!
 *
 * Reference: feature-mvi/src/main/java/com/gyg/codelab/movies/mvi/search/reducer/ObserveSearchFavoritesReducer.kt
 */

// TODO: Implement ObserveSearchFavoritesReducer class here
