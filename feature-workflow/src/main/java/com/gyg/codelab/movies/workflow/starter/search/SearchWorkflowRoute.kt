package com.gyg.codelab.movies.workflow.starter.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.ui.screens.SearchScreen

/**
 * This composable uses the starter workflow for the Search feature.
 *
 * It collects query and searchResults from the ViewModel, passes them to SearchScreen component,
 * and handles user interactions (query changes, movie clicks, favorite toggles).
 *
 * Note: This Route is already implemented below. You need to:
 * 1. First implement SearchWorkflow in SearchWorkflow.kt
 * 2. Then implement SearchWorkflowViewModel
 * 3. This Route will work once those are complete
 *
 * Pattern (based on actual solution):
 * @Composable
 * fun SearchWorkflowRoute(
 *   viewModel: SearchWorkflowViewModel,
 *   onMovieClick: (Movie) -> Unit,
 * ) {
 *   val query by viewModel.query.collectAsStateWithLifecycle()
 *   val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()
 *
 *   SearchScreen(
 *     query = query,
 *     searchResults = searchResults,
 *     onQueryChange = { viewModel.performSearch(it) },
 *     onMovieClick = onMovieClick,
 *     onToggleFavorite = { viewModel.toggleFavorite(it) }
 *   )
 * }
 *
 * Note: Unlike other patterns, this doesn't collect a rendering object.
 * Instead, it collects individual flows (query and searchResults) from the ViewModel.
 *
 * Reference: feature-workflow/src/main/java/com/gyg/codelab/movies/workflow/search/SearchWorkflowRoute.kt
 */
@Composable
fun SearchWorkflowRoute(
  viewModel: SearchWorkflowViewModel,
  onMovieClick: (Movie) -> Unit,
) {
  //TODO : collect only rendering here and clean up query and searchResults
  val query by viewModel.query.collectAsStateWithLifecycle()
  val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()

  SearchScreen(
    query = query,
    searchResults = searchResults,
    onQueryChange = { viewModel.performSearch(it) },
    onMovieClick = onMovieClick,
    onToggleFavorite = { //TODO: implement toggle in the workflow and call it here
      }
  )
}
