package com.gyg.codelab.movies.mvi.starter.home.reducer

import com.gyg.codelab.movies.domain.repository.FavoritesRepository
import com.gyg.codelab.movies.mvi.starter.home.HomeEvent
import com.gyg.codelab.movies.mvi.starter.home.HomeState
import com.gyg.codelab.movies.mvi.state_manager.Reducer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Reducer for toggling favorite status of a movie
 * Triggers repository update which will eventually update state via SetFavoriteMovies
 */
class ToggleHomeFavoriteReducer(
    private val favoritesRepository: FavoritesRepository,
    private val scope: CoroutineScope,
) : Reducer<HomeState, HomeEvent.ToggleFavorite> {
    override suspend fun reduce(state: HomeState, event: HomeEvent.ToggleFavorite): HomeState {
        scope.launch {
            favoritesRepository.toggleFavorite(event.movie)
        }
        return state
    }
}
