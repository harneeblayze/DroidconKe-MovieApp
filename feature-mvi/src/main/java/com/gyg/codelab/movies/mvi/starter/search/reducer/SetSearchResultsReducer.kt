package com.gyg.codelab.movies.mvi.starter.search.reducer

/**
 * TODO: Implement SetSearchResultsReducer
 *
 * This is a simple synchronous reducer that updates the state with search results.
 *
 * Pattern:
 * - Take SearchState and SearchEvent.SetSearchResults
 * - Return new state with updated results and isLoading = false
 *
 * Example:
 * class SetSearchResultsReducer : Reducer<SearchState, SearchEvent.SetSearchResults> {
 *   override suspend fun reduce(
 *     state: SearchState,
 *     event: SearchEvent.SetSearchResults
 *   ): SearchState {
 *     return state.copy(
 *       results = event.movies,
 *       isLoading = false,
 *       error = null
 *     )
 *   }
 * }
 *
 * Reference: feature-mvi/src/main/java/com/gyg/codelab/movies/mvi/starter/home/reducer/SetCategoryMoviesReducer.kt
 */

// TODO: Implement SetSearchResultsReducer class here
