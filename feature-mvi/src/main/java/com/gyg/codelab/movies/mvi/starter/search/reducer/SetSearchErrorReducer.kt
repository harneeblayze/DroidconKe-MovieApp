package com.gyg.codelab.movies.mvi.starter.search.reducer

/**
 * TODO: Implement SetSearchErrorReducer
 *
 * This reducer updates the error state when search fails.
 *
 * RESPONSIBILITIES:
 * - Set the error message
 * - Set isSearching to false (stop showing loading spinner)
 *
 * WHEN IS THIS USED?
 * - PerformSearchReducer publishes SetSearchError when API call fails
 * - Network errors, timeout errors, API errors, etc.
 *
 * IMPLEMENTATION:
 * This is a SYNC reducer (pure function)
 *
 * Example:
 * class SetSearchErrorReducer : Reducer<SearchState, SearchEvent.SetSearchError> {
 *   override suspend fun reduce(state: SearchState, event: SearchEvent.SetSearchError): SearchState {
 *     // TODO: Return state.copy(
 *     //   error = event.error,
 *     //   isSearching = false
 *     // )
 *   }
 * }
 *
 * UI USAGE:
 * if (uiState.error != null) {
 *   Text("Error: ${uiState.error}")  // Show error message
 * }
 *
 * FLOW:
 * User searches for "Batman"
 *     ↓
 * Network error occurs
 *     ↓
 * SetSearchError("Network error") published
 *     ↓
 * SetSearchErrorReducer updates state
 *     ↓
 * UI shows error message
 *
 * NOTE: Don't clear searchResults!
 * If there were previous results, keep showing them even if error occurs.
 * User can still see old results while error message is shown.
 *
 * Reference: feature-mvi/src/main/java/com/gyg/codelab/movies/mvi/search/reducer/SetSearchErrorReducer.kt
 */

// TODO: Implement SetSearchErrorReducer class here
