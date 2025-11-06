package com.gyg.codelab.movies.mvi.starter.home.reducer

import com.gyg.codelab.movies.mvi.starter.home.CategoryState
import com.gyg.codelab.movies.mvi.starter.home.HomeEvent
import com.gyg.codelab.movies.mvi.starter.home.HomeState
import com.gyg.codelab.movies.mvi.state_manager.Reducer

/**
 * Generic reducer for setting error in a category
 */
class SetCategoryErrorReducer : Reducer<HomeState, HomeEvent.SetCategoryError> {
    override suspend fun reduce(state: HomeState, event: HomeEvent.SetCategoryError): HomeState {
        return state.copy(
            movies = state.movies + (event.category to CategoryState.Error(event.message)),
        )
    }
}
