package com.gyg.codelab.movies.mvi.starter.home.reducer

import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.domain.model.MovieCategory
import com.gyg.codelab.movies.domain.repository.FavoritesRepository
import com.gyg.codelab.movies.domain.repository.MoviesRepository
import com.gyg.codelab.movies.mvi.starter.home.HomeEvent
import com.gyg.codelab.movies.mvi.starter.home.HomeState
import com.gyg.codelab.movies.mvi2.state_manager.Reducer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

/**
 * Reducer for loading all movies
 * Uses reactive Flow pattern to combine movies with favorites
 * Automatically reacts to favorite changes and re-emits movies with updated favorite status
 */
class LoadAllMoviesReducer(
  private val repository: MoviesRepository,
  private val favoritesRepository: FavoritesRepository,
  private val scope: CoroutineScope,
  private val publishEvent: (HomeEvent) -> Unit
) : Reducer<HomeState, HomeEvent.LoadAllMovies> {

  private val refreshFlow = MutableStateFlow(0L)
  private var observingJob: Job? = null

  override suspend fun reduce(state: HomeState, event: HomeEvent.LoadAllMovies): HomeState {
    // Only start observing once, subsequent calls trigger refresh
    if (observingJob == null) {
      observingJob = scope.launch {
        MovieCategory.entries.forEach { category ->
          launch {
            // Combine favorites flow with movies flow
            combine(
              favoritesRepository.getFavoriteMovies(),
              channelFlow {
                // Initial load
                send(repository.getMovies(category))
                // Refresh on trigger
                refreshFlow.collect {
                  send(repository.getMovies(category))
                }
              }
            ) { favorites, moviesResult ->
              // Apply favorite status to movies
              val favoriteIds = favorites.map { it.id }.toSet()
              moviesResult.fold(
                onSuccess = { movies ->
                  val moviesWithFavorites = movies.map { movie ->
                    movie.copy(isFavorite = favoriteIds.contains(movie.id))
                  }
                  HomeEvent.SetCategoryMovies(category, moviesWithFavorites)
                },
                onFailure = { error ->
                  HomeEvent.SetCategoryError(category, error.message ?: "Unknown error")
                }
              )
            }.collect { event ->
              publishEvent(event)
            }
          }
        }
      }
    } else {
      // Trigger refresh for all categories
      refreshFlow.value = System.currentTimeMillis()
    }

    return state
  }
}
