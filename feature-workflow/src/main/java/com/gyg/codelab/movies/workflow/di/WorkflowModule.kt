package com.gyg.codelab.movies.workflow.di

import com.gyg.codelab.movies.domain.repository.FavoritesRepository
import com.gyg.codelab.movies.domain.repository.MoviesRepository
import com.gyg.codelab.movies.domain.repository.SearchRepository
import com.gyg.codelab.movies.workflow.favorites.FavoritesWorkflowViewModel
import com.gyg.codelab.movies.workflow.home.HomeWorkflowViewModel
import com.gyg.codelab.movies.workflow.search.SearchWorkflowViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for providing workflow dependencies.
 *
 * Updated to follow the same architecture patterns as MVVM and MVI2 modules:
 * - Injects MoviesRepository for movie catalog
 * - Injects SearchRepository for search functionality
 * - Injects FavoritesRepository for favorites management
 *
 * Provides both the unified MoviesWorkflow and feature-specific workflows.
 */
val workflowModule = module {



  // ========== HOME FEATURE ==========

  // Provide HomeWorkflowViewModel (creates MovieCategoryWorkflow and HomeWorkflow internally)
  // This is necessary because MovieCategoryWorkflow needs viewModelScope
  viewModel {
    HomeWorkflowViewModel(
      moviesRepository = get(),
      favoritesRepository = get()
    )
  }

  // ========== SEARCH FEATURE ==========

  // Provide SearchWorkflowViewModel
  viewModel {
    SearchWorkflowViewModel(
      searchRepository = get(),
      favoritesRepository = get()
    )
  }

  // ========== FAVORITES FEATURE ==========

  // Provide FavoritesWorkflowViewModel
  viewModel {
    FavoritesWorkflowViewModel(
      favoritesRepository = get()
    )
  }
}