package com.gyg.codelab.movies.workflow.home

import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.domain.model.MovieCategory
import com.gyg.codelab.movies.domain.repository.FavoritesRepository
import com.gyg.codelab.movies.domain.repository.MoviesRepository
import com.gyg.codelab.movies.ui.screens.MovieSection
import com.gyg.codelab.movies.workflow.home.MovieCategoryState.Error
import com.gyg.codelab.movies.workflow.home.MovieCategoryState.Loaded
import com.gyg.codelab.movies.workflow.home.MovieCategoryState.Loading
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.StatefulWorkflow.RenderContext
import com.squareup.workflow1.action
import com.squareup.workflow1.asWorker
import com.squareup.workflow1.runningWorker
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlin.time.measureTimedValue

private typealias MovieContext = RenderContext<MovieCategoryProps, MovieCategoryState, Nothing>

data class MovieCategoryProps(
  val category: MovieCategory,
  val loadNumber: Long,
)

class MovieCategoryWorkflow(
  private val repository: MoviesRepository,
  private val favoritesRepository: FavoritesRepository,
) : StatefulWorkflow<MovieCategoryProps, MovieCategoryState, Nothing, MovieSection>() {

  override fun initialState(
    props: MovieCategoryProps, snapshot: Snapshot?
  ): MovieCategoryState = Loading

  override fun onPropsChanged(
    old: MovieCategoryProps,
    new: MovieCategoryProps,
    state: MovieCategoryState
  ): MovieCategoryState {
    return Loading
  }

  override fun render(
    renderProps: MovieCategoryProps,
    renderState: MovieCategoryState,
    context: RenderContext<MovieCategoryProps, MovieCategoryState, Nothing>
  ): MovieSection {

    val category = renderProps.category
    context.observeMovies(renderProps)

    return when (renderState) {
      is Loading -> {
        // Return section with loading state
        if (category == MovieCategory.TRENDING) {
          MovieSection.Carousel(
            category = category,
            movies = flowOf(emptyList()),
            error = null
          )
        } else {
          MovieSection.Regular(
            category = category,
            movies = flowOf(emptyList()),
            title = getCategoryTitle(category),
            isLoading = true,
            error = null
          )
        }
      }

      is Loaded -> {
        // Return section with loaded movies
        if (category == MovieCategory.TRENDING) {
          MovieSection.Carousel(
            category = category,
            movies = kotlinx.coroutines.flow.flowOf(renderState.movies),
            error = null
          )
        } else {
          MovieSection.Regular(
            category = category,
            movies = kotlinx.coroutines.flow.flowOf(renderState.movies),
            title = getCategoryTitle(category),
            isLoading = false,
            error = null
          )
        }
      }

      is Error -> {
        // Return section with error state
        if (category == MovieCategory.TRENDING) {
          MovieSection.Carousel(
            category = category,
            movies = kotlinx.coroutines.flow.flowOf(emptyList()),
            error = renderState.error
          )
        } else {
          MovieSection.Regular(
            category = category,
            movies = kotlinx.coroutines.flow.flowOf(emptyList()),
            title = getCategoryTitle(category),
            isLoading = false,
            error = renderState.error
          )
        }
      }
    }
  }

  /**
   * Maps MovieCategory to display title
   */
  private fun getCategoryTitle(category: MovieCategory): String {
    return when (category) {
      MovieCategory.POPULAR -> "Popular Movies"
      MovieCategory.TOP_RATED -> "Top Rated"
      MovieCategory.UPCOMING -> "Upcoming Movies"
      MovieCategory.NOW_PLAYING -> "Now Playing"
      MovieCategory.TRENDING -> "Trending Now"
    }
  }

  //snapshotState is Workflow's mechanism for state persistence,
  // allowing workflows to survive process death and configuration changes,
  // particularly important for Android applications.

  override fun snapshotState(state: MovieCategoryState) = null

  private fun MovieContext.observeMovies(
    renderProps: MovieCategoryProps,
  ) {
    runningWorker(
      worker =
        // Combine favorites flow with movies flow
        combine(
          favoritesRepository.getFavoriteMovies(),
          flow {
            delay(300)
            emit(repository.getMovies(renderProps.category))
          }
        ) { favorites, moviesResult ->
          // Apply favorite status to movies
          val favoriteIds = favorites.map { it.id }.toSet()
          moviesResult.fold(onSuccess = { movies ->
            val moviesWithFavorites = movies.map { movie ->
              movie.copy(isFavorite = favoriteIds.contains(movie.id))
            }
            Result.success(moviesWithFavorites)
          }, onFailure = { error ->
            Result.failure(error)
          })
        }.asWorker(),
      key = "loading_movies_${renderProps.category}_${renderProps.loadNumber}"
    ) { result ->
      action("loaded_movies") {
        state = result.fold(
          onSuccess = {
            when (val currentState = state) {
              is Loaded -> currentState.copy(movies = it)
              else -> {
                Loaded(movies = it)
              }
            }
          },
          onFailure = {
            Error(
              error = it.message ?: "Unknown error"
            )
          }
        )
      }
    }
  }
}

suspend inline fun <T> executeForAtLeast(
  minDuration: Long,
  operation: () -> T,
): T {
  val timedValue = measureTimedValue {
    operation()
  }
  val delay = minDuration - timedValue.duration.inWholeMilliseconds
  if (delay > 0) {
    delay(delay)
  }
  return timedValue.value
}

sealed interface MovieCategoryState {

  data object Loading : MovieCategoryState

  data class Loaded(
    val movies: List<Movie>,
  ) : MovieCategoryState

  data class Error(
    val error: String
  ) : MovieCategoryState
}