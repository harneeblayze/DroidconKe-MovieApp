package com.gyg.codelab.movies.workflow.starter.search

import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.domain.repository.FavoritesRepository
import com.gyg.codelab.movies.domain.repository.SearchRepository

/**
 * TODO: Implement SearchWorkflow - A StatefulWorkflow for search functionality
 *
 * In Workflow architecture, workflows are state machines that manage UI state and business logic.
 *
 * Study HomeWorkflow and MovieCategoryWorkflow to understand:
 * - How to extend StatefulWorkflow
 * - How to define State (can be sealed interface or data class)
 * - How to define Rendering (data class with UI data and event handlers)
 * - How to implement required methods (initialState, render, snapshotState)
 * - How to use runningWorker for async operations
 * - How to use eventHandler for user actions
 * - How to use action() for state updates
 *
 * Components you need to create:
 *
 * 1. SearchState - Represents different search states
 *    Consider: Empty state, Searching state, Error state
 *
 * 2. SearchRendering - What UI needs
 *    Consider: query, results, loading state, error, event handlers
 *
 * 3. SearchWorkflow class - The state machine
 *    Must extend: StatefulWorkflow<Unit, SearchState, Nothing, SearchRendering>
 *
 * Key concepts to implement:
 * - Use eventHandler for query changes (updates state)
 * - Use runningWorker to perform search (call repository)
 * - Use combine() to merge search results with favorites
 * - Use action() to handle worker results and update state
 * - Key workers by query to restart when query changes
 *
 * Reference: feature-workflow/src/main/java/com/gyg/codelab/movies/workflow/search/SearchWorkflow.kt
 */

// TODO: Define SearchState sealed interface or data class

// TODO: Define SearchRendering data class

// TODO: Implement SearchWorkflow class
