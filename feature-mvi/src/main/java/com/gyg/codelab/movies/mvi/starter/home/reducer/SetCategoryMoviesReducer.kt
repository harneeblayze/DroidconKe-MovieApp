package com.gyg.codelab.movies.mvi.starter.home.reducer

import com.gyg.codelab.movies.mvi.starter.home.HomeEvent
import com.gyg.codelab.movies.mvi.starter.home.HomeState
import com.gyg.codelab.movies.mvi.starter.home.CategoryState
import com.gyg.codelab.movies.mvi2.state_manager.Reducer

/**
 * Generic reducer for setting movies in a category
 * Movies already have favorite status applied from the reactive Flow in load reducers
 */
class SetCategoryMoviesReducer : Reducer<HomeState, HomeEvent.SetCategoryMovies> {
  override suspend fun reduce(state: HomeState, event: HomeEvent.SetCategoryMovies): HomeState {
    return state.copy(
      movies = state.movies + (event.category to CategoryState.Success(event.movies))
    )
  }
}
