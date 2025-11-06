package com.gyg.codelab.movies.mvi.starter.search

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Container name for the Search MVI scope
 */
const val SEARCH_MVI_STARTER_CONTAINER = "SEARCH_MVI_STARTER_CONTAINER"

/**
 * Koin module for MVI Search feature (Starter)
 *
 * This module is FULLY IMPLEMENTED - DI setup is infrastructure code.
 * Your task is to implement the actual components (State, Events, Reducers, ViewModel).
 *
 * Once you implement SearchState, SearchEvent, and the reducers, uncomment the code below.
 * This module will automatically wire everything together with proper dependency injection.
 *
 * What this module does:
 * - Registers SearchMviViewModel with StateManager injection
 * - Creates and configures StateManager with initial state
 * - Registers all reducers with their required dependencies
 * - All dependencies (repositories, scope, etc.) are automatically injected by Koin
 */
val searchStarterMviModule = module {

    scope(named(SEARCH_MVI_STARTER_CONTAINER)) {

        // ViewModel - injected with StateManager and optional Transformer
        viewModelOf(::SearchMviViewModel)

        // Optional: Transformer (uncomment if you create one)
        // scoped { SearchTransformer() }

        // State Manager - manages state and event processing
        // TODO: Once you implement SearchState and SearchEvent, uncomment this:
    /*
    scoped<StateManager<*, *>> {
      StateManagerImpl<SearchState, SearchEvent>(
        initialState = SearchState.initial(),
        scope = this@scoped,
        lazyViewModelScope = { get() },
      )
    }
     */

        // ========== REDUCERS ==========
        // All dependencies (repositories, scope, etc.) are injected automatically by Koin
        // TODO: Once you implement the reducers, uncomment these:

    /*
    // Search movies reducer - handles async search with reactive favorites
    scopedReducer<SearchState, SearchEvent.SearchMovies> {
      SearchMoviesReducer(
        searchRepository = get(),      // Injected
        favoritesRepository = get(),   // Injected
        scope = get(),                 // Injected
        publishEvent = { event ->
          get<StateManager<SearchState, SearchEvent>>().publishEvent(event)
        }
      )
    }

    // Set search results reducer - updates state with results
    scopedReducer<SearchState, SearchEvent.SetSearchResults> {
      SetSearchResultsReducer()
    }

    // Toggle favorite reducer - handles favorite toggling
    scopedReducer<SearchState, SearchEvent.ToggleFavorite> {
      ToggleSearchFavoriteReducer(
        favoritesRepository = get(),   // Injected
        scope = get()                  // Injected
      )
    }
     */
    }
}
