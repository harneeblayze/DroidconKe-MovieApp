package com.gyg.codelab.movies.workflow.starter.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gyg.codelab.movies.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * TODO: Implement FavoritesWorkflowViewModel using Workflow pattern
 *
 * This ViewModel should:
 * 1. Create a FavoritesWorkflow instance (from starter package)
 * 2. Use renderWorkflowIn() to run the workflow
 * 3. Expose favoritesRendering as StateFlow
 *
 * Steps:
 * 1. First, implement FavoritesWorkflow in FavoritesWorkflow.kt
 * 2. Import FavoritesWorkflow and FavoritesRendering from this starter package
 * 3. Create FavoritesWorkflow instance with favoritesRepository and viewModelScope
 * 4. Use renderWorkflowIn() with the workflow
 * 5. Map the rendering and stateIn() to get StateFlow
 *
 * Key imports needed:
 * import com.gyg.codelab.movies.workflow.starter.favorites.FavoritesWorkflow
 * import com.gyg.codelab.movies.workflow.starter.favorites.FavoritesRendering
 * import com.squareup.workflow1.renderWorkflowIn
 * import kotlinx.coroutines.flow.map
 * import kotlinx.coroutines.flow.stateIn
 * import kotlinx.coroutines.flow.SharingStarted
 *
 * Pattern (based on actual solution):
 * class FavoritesWorkflowViewModel(
 *   private val favoritesRepository: FavoritesRepository
 * ) : ViewModel() {
 *
 *   private val favoritesWorkflow = FavoritesWorkflow(
 *     favoritesRepository = favoritesRepository,
 *     viewModelScope = viewModelScope,
 *   )
 *
 *   val favoritesRendering: StateFlow<FavoritesRendering> = renderWorkflowIn(
 *     workflow = favoritesWorkflow,
 *     scope = viewModelScope,
 *     props = MutableStateFlow(Unit),
 *     onOutput = { }
 *   )
 *     .map { it.rendering }
 *     .stateIn(
 *       scope = viewModelScope,
 *       started = SharingStarted.WhileSubscribed(5000),
 *       initialValue = FavoritesRendering(
 *         favorites = emptyList(),
 *         onToggleFavourite = {}
 *       )
 *     )
 * }
 *
 * Note: Unlike Search, Favorites doesn't expose toggleFavorite() in ViewModel.
 * The toggle action is handled through rendering.onToggleFavourite in the UI.
 *
 * Reference: feature-workflow/src/main/java/com/gyg/codelab/movies/workflow/favorites/FavoritesWorkflowViewModel.kt
 */
class FavoritesWorkflowViewModel(
  private val favoritesRepository: FavoritesRepository
) : ViewModel() {

  // TODO: Create FavoritesWorkflow instance (after implementing FavoritesWorkflow.kt)
  // private val favoritesWorkflow = FavoritesWorkflow(
  //   favoritesRepository = favoritesRepository,
  //   viewModelScope = viewModelScope,
  // )

  // TODO: Implement rendering using renderWorkflowIn
  // val favoritesRendering: StateFlow<FavoritesRendering> = renderWorkflowIn(
  //   workflow = favoritesWorkflow,
  //   scope = viewModelScope,
  //   props = MutableStateFlow(Unit),
  //   onOutput = { }
  // )
  //   .map { it.rendering }
  //   .stateIn(
  //     scope = viewModelScope,
  //     started = SharingStarted.WhileSubscribed(5000),
  //     initialValue = FavoritesRendering(
  //       favorites = emptyList(),
  //       onToggleFavourite = {}
  //     )
  //   )

  // Placeholder for now - will be replaced with actual rendering
  // val favoritesRendering: StateFlow<FavoritesRendering> = ...
}
