package com.gyg.codelab.movies.mvi.starter.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gyg.codelab.movies.mvi2.state_manager.StateManager
import kotlinx.coroutines.flow.*

/**
 * MVI HomeViewModel for managing home screen state
 *
 * Follows MVI pattern with:
 * - State: HomeState (immutable domain state)
 * - Events: HomeEvent (user actions)
 * - Reducers: Transform state based on events
 * - Transformer: Convert domain state to UI state
 *
 * Favorites are applied reactively by combining Flow from favoritesRepository
 * with movie data in the load reducers. Any favorite change automatically
 * triggers re-emission of movies with updated favorite status.
 */
class HomeMviViewModel(
  private val stateManager: StateManager<HomeState, HomeEvent>,
  private val transformer: HomeTransformer,
) : ViewModel() {

  // Domain state from StateManager
  private val domainState: StateFlow<HomeState> = stateManager.getState()

  /**
   * UI state transformed from domain state
   * Contains movieSections ready for display in LazyMoviesScreen
   */
  val uiState: StateFlow<HomeUIState> = domainState
    .map { state -> transformer.transform(state) }
    .onStart {
      // Start processing events
      stateManager.startProcessingEvents()

      // Load initial data (favorites are now loaded reactively within this)
      handleEvent(HomeEvent.LoadAllMovies)
    }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5000),
      initialValue = HomeUIState.initial()
    )


  /**
   * Handle incoming events
   * Events are processed by reducers via the StateManager
   */
  fun handleEvent(event: HomeEvent) {
    stateManager.publishEvent(event)
  }

  /**
   * Toggle favorite status of a movie
   * Dispatches event - reducer handles the repository update
   * State will be updated reactively via combined flows in load reducers
   */
  fun toggleFavorite(movie: com.gyg.codelab.movies.domain.model.Movie) {
    handleEvent(HomeEvent.ToggleFavorite(movie))
  }

  override fun onCleared() {
  }
}
