package com.gyg.codelab.movies.mvi.starter.search

import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.mvi2.state_manager.State

/**
 * TODO: Implement SearchState for the Search feature
 *
 * In MVI, State represents the complete UI state at any point in time.
 * State should be immutable (use data class with val properties).
 *
 * Study HomeState to understand:
 * - How to structure state as a data class
 * - How to implement the State interface
 * - How to provide an initial() factory function
 *
 * What search state needs to hold:
 * - Current search query
 * - Search results (list of movies with favorite status)
 * - Loading state
 * - Error message (if any)
 *
 * Reference: feature-mvi/src/main/java/com/gyg/codelab/movies/mvi/starter/home/HomeState.kt
 */

// TODO: Implement SearchState data class here
// Must implement State interface
// Must have a companion object with initial() function
