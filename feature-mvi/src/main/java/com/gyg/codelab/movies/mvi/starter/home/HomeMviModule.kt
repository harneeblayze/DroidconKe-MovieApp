package com.gyg.codelab.movies.mvi.starter.home

import com.gyg.codelab.movies.mvi.starter.home.reducer.*
import com.gyg.codelab.movies.mvi.state_manager.StateManager
import com.gyg.codelab.movies.mvi.state_manager.StateManagerImpl
import com.gyg.codelab.movies.mvi.state_manager.scopedReducer
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Container name for the Home MVI scope
 */
const val HOME_MVI_STARTER_CONTAINER = "HOME_MVI_STARTER_CONTAINER"

/**
 * Koin module for MVI Home feature (Starter)
 *
 * Registers:
 * - StateManager for home state
 * - Transformer for state transformation
 * - All reducers for home events (load, set, error, favorites)
 * - HomeMviViewModel
 */
val homeStarterMviModule = module {

    // Scoped dependencies for the Home feature
    scope(named(HOME_MVI_STARTER_CONTAINER)) {

        viewModelOf(::HomeMviViewModel)

        // Transformer (no dependencies)
        scoped {
            HomeTransformer()
        }

        // State Manager - manages state and event processing
        scoped<StateManager<*, *>> {
            StateManagerImpl<HomeState, HomeEvent>(
                initialState = HomeState.initial(),
                scope = this@scoped,
                lazyViewModelScope = { get() },
            )
        }

        // ========== LOAD REDUCERS (Async operations) ==========

        // Load all movies - triggers other load events
        scopedReducer<HomeState, HomeEvent.LoadAllMovies> {
            LoadAllMoviesReducer(
                repository = get(),
                favoritesRepository = get(),
                scope = get(),
                publishEvent = { event ->
                    get<StateManager<HomeState, HomeEvent>>().publishEvent(event)
                },
            )
        }

        // ========== SET REDUCERS (Update state with results) ==========

        // Generic set category movies reducer - handles all categories
        scopedReducer<HomeState, HomeEvent.SetCategoryMovies> {
            SetCategoryMoviesReducer()
        }

        // ========== ERROR REDUCERS (Handle failures per category) ==========

        // Generic set category error reducer - handles all categories
        scopedReducer<HomeState, HomeEvent.SetCategoryError> {
            SetCategoryErrorReducer()
        }

        // ========== FAVORITES REDUCER ==========

        // Toggle favorite (handles favorite toggling)
        scopedReducer<HomeState, HomeEvent.ToggleFavorite> {
            ToggleHomeFavoriteReducer(
                favoritesRepository = get(),
                scope = get(),
            )
        }

        // ========== UTILITY REDUCERS ==========

        // Refresh (triggers load all)
        scopedReducer<HomeState, HomeEvent.Refresh> {
            RefreshHomeReducer(
                publishEvent = { event ->
                    get<StateManager<HomeState, HomeEvent>>().publishEvent(event)
                },
            )
        }
    }
}
