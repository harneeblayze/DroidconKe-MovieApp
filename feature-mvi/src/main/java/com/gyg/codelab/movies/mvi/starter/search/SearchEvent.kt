package com.gyg.codelab.movies.mvi.starter.search

import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.mvi2.state_manager.Event

/**
 * TODO: Implement SearchEvent for the Search feature
 *
 * In MVI, Events represent user actions and system events.
 * Events should be defined as a sealed class hierarchy.
 *
 * Study HomeEvent to understand:
 * - How to structure events as a sealed class
 * - How to implement the Event interface
 * - How to define different event types (object vs data class)
 *
 * Search events you need:
 * - User initiates search with a query
 * - System sets search results (after loading)
 * - System sets error (if search fails)
 * - User toggles favorite on a movie
 * - User clears search (optional)
 *
 * Reference: feature-mvi/src/main/java/com/gyg/codelab/movies/mvi/starter/home/HomeEvent.kt
 */

// TODO: Implement SearchEvent sealed class here
 sealed class SearchEvent : Event {
   // TODO: Implement events you need here
 }
