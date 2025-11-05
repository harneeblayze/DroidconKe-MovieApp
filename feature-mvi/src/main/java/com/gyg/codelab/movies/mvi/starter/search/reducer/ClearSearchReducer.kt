package com.gyg.codelab.movies.mvi.starter.search.reducer

/**
 * TODO: Implement ClearSearchReducer
 *
 * This reducer clears the search query and results when the user clears the search.
 *
 * RESPONSIBILITIES:
 * - Clear the search query (empty string)
 * - Clear search results (empty list)
 * - Clear the searching state (false)
 * - Clear any error messages (null)
 * - PRESERVE favoriteMovieIds (don't clear favorites!)
 *
 * WHY PRESERVE favoriteMovieIds?
 * Favorites persist across searches. If user favorites a movie during search,
 * then clears search, the favorite should still be remembered.
 *
 * IMPLEMENTATION:
 * This is a SYNC reducer (no async operations)
 * Just return a new state with updated values
 *
 * Example:
 * class ClearSearchReducer : Reducer<SearchState, SearchEvent.ClearSearch> {
 *   override suspend fun reduce(state: SearchState, event: SearchEvent.ClearSearch): SearchState {
 *     // TODO: Return state.copy(...) with:
 *     // - query = ""
 *     // - searchResults = emptyList()
 *     // - isSearching = false
 *     // - error = null
 *     // Note: Don't include favoriteMovieIds (preserve it!)
 *   }
 * }
 *
 * WHEN IS THIS USED?
 * - User taps the X button to clear search
 * - User clears the search text field
 * - PerformSearchReducer publishes ClearSearch when query becomes empty
 *
 * Reference: feature-mvi/src/main/java/com/gyg/codelab/movies/mvi/search/reducer/ClearSearchReducer.kt
 */

// TODO: Implement ClearSearchReducer class here
