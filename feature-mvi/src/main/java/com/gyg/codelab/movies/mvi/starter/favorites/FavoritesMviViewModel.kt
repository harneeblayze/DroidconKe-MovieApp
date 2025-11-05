package com.gyg.codelab.movies.mvi.starter.favorites

import androidx.lifecycle.ViewModel
import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.mvi2.favorites.FavoritesState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf

/**
 * TODO: Implement FavoritesMviViewModel using MVI pattern
 *
 * MVI Components needed:
 * 1. FavoritesState - Domain state (favorite movies list, loading, error)
 * 2. FavoritesEvent - Events (LoadFavorites, SetFavorites, ToggleFavorite)
 * 3. LoadFavoritesReducer - Handle LoadFavorites event, observe favorites repository
 * 4. ToggleFavoriteReducer - Handle ToggleFavorite event
 * 5. StateManager - Manage state and reducers
 * 6. StateTransformer (optional) - Transform domain state to UI state if needed
 *
 * Pattern:
 * - Use StateManager to manage FavoritesState
 * - Define reducers for each event type
 * - Observe favoritesRepository.getFavoriteMovies() in load reducer
 * - Expose uiState as StateFlow
 *
 * Reference: feature-mvi/src/main/java/com/gyg/codelab/movies/mvi/favorites/FavoritesMviViewModel.kt
 * Reference: feature-mvvm/src/main/java/com/gyg/codelab/movies/mvvm/favorites/FavoritesViewModel.kt
 */
class FavoritesMviViewModel(
  // TODO: Inject StateManager<FavoritesState, FavoritesEvent>
  // TODO: Inject StateTransformer if needed
) : ViewModel() {

  // TODO: Implement domain state from StateManager
   //private val domainState: StateFlow<FavoritesState> = stateManager.getState()

  // TODO: Implement UI state (either transform from domain state or use domain state directly)
  val favoriteMovies: Flow<List<Movie>> = flowOf(emptyList())

  /**
   * TODO: Implement handleEvent function
   * - Publish events to StateManager
   */
  fun handleEvent(event: Any /* TODO: Replace with FavoritesEvent */) {
    // TODO: stateManager.publishEvent(event)
  }

  /**
   * TODO: Implement toggleFavorite function
   * - Create ToggleFavorite event with movie
   * - Call handleEvent(event)
   */
  fun toggleFavorite(movie: Movie) {
    // TODO: fire event for toggle favorite
  }
}
