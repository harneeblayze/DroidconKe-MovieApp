package com.gyg.codelab.movies.mvi.starter.search.reducer

/**
 * TODO: Implement ClearSearchErrorReducer
 *
 * This reducer clears the error message from state.
 *
 * RESPONSIBILITIES:
 * - Set error to null
 *
 * WHEN IS THIS USED?
 * - User starts a new search (clear old errors)
 * - User dismisses error message
 * - Before showing new search results
 *
 * IMPLEMENTATION:
 * This is a SYNC reducer (pure function)
 * Simplest reducer in the search feature!
 *
 * Example:
 * class ClearSearchErrorReducer : Reducer<SearchState, SearchEvent.ClearSearchError> {
 *   override suspend fun reduce(
 *     state: SearchState,
 *     event: SearchEvent.ClearSearchError
 *   ): SearchState {
 *     // TODO: Return state.copy(error = null)
 *   }
 * }
 *
 * FLOW:
 * Error shown: "Network error"
 *     ↓
 * User types new query
 *     ↓
 * ClearSearchError published
 *     ↓
 * Error message hidden
 *     ↓
 * New search starts
 *
 * UI PATTERN:
 * if (uiState.error != null) {
 *   Snackbar(
 *     message = uiState.error,
 *     action = { viewModel.clearError() }  // Publishes ClearSearchError
 *   )
 * }
 *
 * Reference: feature-mvi/src/main/java/com/gyg/codelab/movies/mvi/search/reducer/ClearSearchErrorReducer.kt
 */

// TODO: Implement ClearSearchErrorReducer class here
