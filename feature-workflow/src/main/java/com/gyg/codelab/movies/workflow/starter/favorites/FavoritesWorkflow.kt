package com.gyg.codelab.movies.workflow.starter.favorites

/**
 * TODO: Implement FavoritesWorkflow - A StatefulWorkflow for favorites display
 *
 * In Workflow architecture, workflows are state machines that manage UI state and business logic.
 *
 * Study HomeWorkflow and FavoritesWorkflow (from main package) to understand:
 * - How to extend StatefulWorkflow
 * - How state can be simple (just List<Movie>) or complex
 * - How to define Rendering (data class with UI data and event handlers)
 * - How to implement required methods (initialState, render, snapshotState)
 * - How to use runningWorker to observe Flow from repository
 * - How to use eventHandler for user actions
 * - How to use action() for state updates
 *
 * Components you need to create:
 *
 * 1. FavoritesRendering - What UI needs
 *    Consider: favorites list, event handlers
 *
 * 2. FavoritesWorkflow class - The state machine
 *    Must extend: StatefulWorkflow<Unit, List<Movie>, Nothing, FavoritesRendering>
 *    Note: State can be simple - just List<Movie> is enough!
 *
 * Key concepts to implement:
 * - Use eventHandler for toggle favorite action
 * - Use runningWorker to observe favoritesRepository.getFavoriteMovies()
 * - Use action() to update state when favorites change
 * - Worker updates state automatically when repository emits new data
 *
 * Reference: feature-workflow/src/main/java/com/gyg/codelab/movies/workflow/favorites/FavoritesWorkflow.kt
 */

// TODO: Define FavoritesRendering data class

// TODO: Implement FavoritesWorkflow class
