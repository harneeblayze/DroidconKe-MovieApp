package com.gyg.codelab.movies.mvi.starter.favorites

import com.gyg.codelab.movies.mvi2.state_manager.StateManager
import com.gyg.codelab.movies.mvi2.state_manager.StateManagerImpl
import com.gyg.codelab.movies.mvi2.state_manager.scopedReducer
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Koin module for MVI Favorites feature (Starter)
 *
 * This module is FULLY IMPLEMENTED - DI setup is infrastructure code.
 * Your task is to implement the actual components (State, Events, Reducers, ViewModel).
 *
 * Once you implement FavoritesState, FavoritesEvent, and the reducers, uncomment the code below.
 * This module will wire everything together automatically.
 *
 * What this module does:
 * - Registers FavoritesMviViewModel
 * - Creates and configures StateManager
 * - Registers all reducers for event processing
 */

/**
 * Container name for the Favorites MVI scope
 */
const val FAVORITES_MVI_STARTER_CONTAINER = "FAVORITES_MVI_STARTER_CONTAINER"

/**
 * Koin module for MVI Favorites feature (Starter)
 *
 * This module is FULLY IMPLEMENTED - DI setup is infrastructure code.
 * Your task is to implement the actual components (State, Events, Reducers, ViewModel).
 *
 * Once you implement FavoritesState, FavoritesEvent, and the reducers, uncomment the code below.
 * This module will automatically wire everything together with proper dependency injection.
 *
 * What this module does:
 * - Registers FavoritesMviViewModel with StateManager injection
 * - Creates and configures StateManager with initial state
 * - Registers all reducers with their required dependencies
 * - All dependencies (repositories, scope, etc.) are automatically injected by Koin
 */
val favoritesStarterMviModule = module {

  scope(named(FAVORITES_MVI_STARTER_CONTAINER)) {

    // ViewModel - injected with StateManager
    viewModelOf(::FavoritesMviViewModel)

    // State Manager - manages state and event processing
    // NOTE: Uncomment this once you've implemented FavoritesState and FavoritesEvent
    /*
    scoped<StateManager<*, *>> {
      StateManagerImpl<FavoritesState, FavoritesEvent>(
        initialState = FavoritesState.initial(),
        scope = this@scoped,
        lazyViewModelScope = { get() },
      )
    }
    */

    // ========== REDUCERS ==========
    // NOTE: Uncomment these once you've implemented the reducers

    /*
    // Load favorites reducer
    scopedReducer<FavoritesState, FavoritesEvent.LoadFavorites> {
      LoadFavoritesReducer(
        favoritesRepository = get(),
        scope = get(),
        publishEvent = { event ->
          get<StateManager<FavoritesState, FavoritesEvent>>().publishEvent(event)
        }
      )
    }

    // Set favorites reducer
    scopedReducer<FavoritesState, FavoritesEvent.SetFavorites> {
      SetFavoritesReducer()
    }

    // Toggle favorite reducer
    scopedReducer<FavoritesState, FavoritesEvent.ToggleFavorite> {
      ToggleFavoriteReducer(
        favoritesRepository = get(),
        scope = get()
      )
    }
    */
  }
}
