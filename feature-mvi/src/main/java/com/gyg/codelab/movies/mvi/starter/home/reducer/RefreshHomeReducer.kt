package com.gyg.codelab.movies.mvi.starter.home.reducer

import com.gyg.codelab.movies.mvi.starter.home.HomeEvent
import com.gyg.codelab.movies.mvi.starter.home.HomeState
import com.gyg.codelab.movies.mvi2.state_manager.Reducer

/**
 * Reducer for refresh
 */
class RefreshHomeReducer(
  private val publishEvent: (HomeEvent) -> Unit
) : Reducer<HomeState, HomeEvent.Refresh> {
  override suspend fun reduce(state: HomeState, event: HomeEvent.Refresh): HomeState {
    publishEvent(HomeEvent.LoadAllMovies)
    return state
  }
}
