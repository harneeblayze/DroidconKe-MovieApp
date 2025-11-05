package com.gyg.codelab.movies.mvi.starter.search.reducer

/**
 * TODO: Implement SetSearchingReducer
 *
 * This reducer updates the isSearching state (for showing/hiding loading spinner).
 *
 * RESPONSIBILITIES:
 * - Update state.isSearching to true or false
 *
 * WHEN IS THIS USED?
 * - PerformSearchReducer publishes SetSearching(true) before API call
 * - PerformSearchReducer publishes SetSearching(false) after API call completes
 *
 * IMPLEMENTATION:
 * This is a SYNC reducer (pure function)
 *
 * Example:
 * class SetSearchingReducer : Reducer<SearchState, SearchEvent.SetSearching> {
 *   override suspend fun reduce(state: SearchState, event: SearchEvent.SetSearching): SearchState {
 *     // TODO: Return state.copy(isSearching = event.isSearching)
 *   }
 * }
 *
 * UI USAGE:
 * if (uiState.isSearching) {
 *   CircularProgressIndicator()  // Show loading spinner
 * }
 *
 * FLOW:
 * User types "Batman"
 *     ↓
 * 300ms passes (debounce)
 *     ↓
 * SetSearching(true) → Spinner shows
 *     ↓
 * API call executes
 *     ↓
 * SetSearchResults(movies) → Results show
 *     ↓
 * SetSearching(false) → Spinner hides
 *
 * Reference: feature-mvi/src/main/java/com/gyg/codelab/movies/mvi/search/reducer/SetSearchingReducer.kt
 */

// TODO: Implement SetSearchingReducer class here
