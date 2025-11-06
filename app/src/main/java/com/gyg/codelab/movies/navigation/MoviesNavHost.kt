package com.gyg.codelab.movies.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.mvvm.starter.home.HomeRoute
import com.gyg.codelab.movies.mvvm.starter.home.HomeViewModel
import com.gyg.codelab.movies.mvvm.starter.search.SearchRoute
import com.gyg.codelab.movies.mvvm.starter.search.SearchViewModel
import com.gyg.codelab.movies.ui.MoviesAppState
import com.gyg.codelab.movies.workflow.starter.favorites.FavoritesWorkflowRoute
import com.gyg.codelab.movies.workflow.starter.favorites.FavoritesWorkflowViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.getKoin
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.qualifier.named
import org.koin.mp.KoinPlatformTools
import org.koin.mp.generateId

/**
 * Top-level navigation graph.
 * Navigation is organized following Now in Android pattern.
 *
 * The NavHost defines routes and instantiates ViewModels.
 * Actual UI composition is delegated to feature-specific route composables.
 */
@OptIn(KoinExperimentalAPI::class)
@Composable
fun MoviesNavHost(
    appState: MoviesAppState,
    onMovieClick: (Movie) -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = "home",
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        // Home screen with movie categories
        composable(route = "home") {
            // MVI version

      /*val viewModel: HomeMviViewModel = getScopedViewModel(container = HOME_MVI_CONTAINER)
      HomeMviRoute(
        viewModel = viewModel,
        onMovieClick = onMovieClick,
        onSearchClick = {
          // Use top-level navigation to properly manage back stack
          appState.navigateToTopLevelDestination(TopLevelDestination.SEARCH)
        }
      )*/

            // MVVM version
            val viewModel: HomeViewModel = koinViewModel()
            HomeRoute(
                viewModel = viewModel,
                onMovieClick = onMovieClick,
                onSearchClick = {
                    // Use top-level navigation to properly manage back stack
                    appState.navigateToTopLevelDestination(TopLevelDestination.SEARCH)
                },
            )

     /* // Workflow version
      val viewModel: HomeWorkflowViewModel = koinViewModel()
      HomeWorkflowRoute(
        viewModel = viewModel,
        onMovieClick = onMovieClick,
        onSearchClick = {
          // Use top-level navigation to properly manage back stack
          appState.navigateToTopLevelDestination(TopLevelDestination.SEARCH)
        }
      )*/
        }

        // Search screen
        composable(route = "search") {
            // Mvvm version
            val viewModel: SearchViewModel = koinViewModel()
            SearchRoute(
                viewModel = viewModel,
                onMovieClick = onMovieClick,
            )

      /*val viewModel: SearchMviViewModel = getScopedViewModel(SEARCH_MVI_CONTAINER)
      SearchMviRoute(
        viewModel = viewModel,
        onMovieClick = onMovieClick,
      )*/

            // Workflow version
      /*val viewModel: SearchWorkflowViewModel = koinViewModel()
      SearchWorkflowRoute(
        viewModel = viewModel,
        onMovieClick = onMovieClick,
      )*/
        }

        // Favorites screen
        composable(route = "favorites") {
           /* // MVVM version
            val viewModel: FavoritesViewModel = koinViewModel()
            FavoritesRoute(
                viewModel = viewModel,
                onMovieClick = onMovieClick,
            )*/

            // MVI version
           /* val viewModel: FavoritesMviViewModel = getScopedViewModel(FAVORITES_MVI_CONTAINER)
            FavoritesMviRoute(
                viewModel = viewModel,
                onMovieClick = onMovieClick,
            )*/

            // Workflow version
            val viewModel: FavoritesWorkflowViewModel = koinViewModel()
            FavoritesWorkflowRoute(
                viewModel = viewModel,
                onMovieClick = onMovieClick,
            )
        }
    }
}

private const val LISTENER_KEY = "DroidconKE2025"

@Composable
private inline fun <reified VM : ViewModel> getScopedViewModel(container: String): VM {
    val scopeId = remember { KoinPlatformTools.generateId() }
    val scope = getKoin().getOrCreateScope(
        scopeId = scopeId,
        qualifier = named(container),
    )
    val viewModel = koinViewModel<VM>(scope = scope)
    scope.declare(viewModel.viewModelScope)

    val listener = viewModel.getCloseable<AutoCloseable>(LISTENER_KEY)
    if (listener == null) {
        viewModel.addCloseable(LISTENER_KEY) {
            scope.close()
        }
    }

    return viewModel
}
