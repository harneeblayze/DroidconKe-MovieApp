package com.gyg.codelab.movies.mvi2.state_manager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gyg.codelab.movies.mvi2.transformer.StateTransformer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

/**
 * Base ViewModel for MVI architecture (simplified)
 *
 * @param S The domain State type
 * @param E The Event type
 * @param View The transformed UI View state type
 */
abstract class MviViewModel<S : State, E : Event, out View : Any>(
  private val stateManager: StateManager<S, E>,
  private val stateTransformer: StateTransformer<S, View>,
) : ViewModel() {

  val viewState: StateFlow<View> by lazy {
    createViewStateFlow()
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = stateTransformer.transform(stateManager.getState().value),
      )
  }

  protected open fun onStateStarted() {}

  private fun createViewStateFlow(): Flow<View> =
    stateManager.getState()
      .map { stateTransformer.transform(it) }
      .onStart {
        stateManager.startProcessingEvents()
        onStateStarted()
      }

  /**
   * Post an event to be processed by reducers
   */
  protected fun postEvent(event: E) {
    stateManager.publishEvent(event)
  }
}
