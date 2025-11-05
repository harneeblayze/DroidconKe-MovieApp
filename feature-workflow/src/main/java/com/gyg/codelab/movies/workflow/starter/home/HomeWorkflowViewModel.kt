package com.gyg.codelab.movies.workflow.starter.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gyg.codelab.movies.domain.repository.FavoritesRepository
import com.gyg.codelab.movies.domain.repository.MoviesRepository
import com.gyg.codelab.movies.workflow.home.HomeRendering
import com.gyg.codelab.movies.workflow.home.HomeWorkflow
import com.gyg.codelab.movies.workflow.home.MovieCategoryWorkflow
import com.squareup.workflow1.renderWorkflowIn
import kotlinx.coroutines.flow.*

/**
 * Workflow Home ViewModel
 *
 * Uses HomeWorkflow which internally manages MovieCategoryWorkflow instances.
 * HomeWorkflow renders all category workflows and combines their output into
 * a HomeRendering with List<MovieSection> for use with common LazyMoviesScreen UI.
 *
 * This demonstrates proper workflow composition where HomeWorkflow orchestrates
 * multiple child MovieCategoryWorkflows.
 */
class HomeWorkflowViewModel(
  moviesRepository: MoviesRepository,
  private val favoritesRepository: FavoritesRepository
) : ViewModel() {

  // Create MovieCategoryWorkflow with viewModelScope
  private val movieCategoryWorkflow = MovieCategoryWorkflow(
    repository = moviesRepository,
    favoritesRepository = favoritesRepository
  )

  // Create HomeWorkflow with the category workflow
  private val homeWorkflow = HomeWorkflow(
    movieCategoryWorkflow = movieCategoryWorkflow,
    viewModelScope = viewModelScope,
    favoritesRepository = favoritesRepository,
  )

  /**
   * Render the HomeWorkflow and extract movie sections from the rendering
   */
  val homeRendering: StateFlow<HomeRendering> = renderWorkflowIn(
    workflow = homeWorkflow,
    scope = viewModelScope,
    props = MutableStateFlow(Unit),
    onOutput = { /* HomeWorkflow has no output */ }
  )
    .map { it.rendering }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5000),
      initialValue = HomeRendering(
        movies = emptyList(),
        onToggleFavourite = {},
        onRetryLoad = {}
      )
    )

}
