package com.gyg.codelab.movies.mvi2.effect

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

/**
 * Consumer for handling side effects in the Movies MVI architecture
 *
 * This class manages the flow of effects that need to be handled by the UI,
 * such as showing toasts, navigation, or other one-time events.
 */
class MoviesEffectConsumer(
  private val scope: CoroutineScope
) {
  private val _effects = MutableSharedFlow<MoviesEffect>()
  val effects: Flow<MoviesEffect> = _effects.asSharedFlow()

  /**
   * Emit an effect to be consumed by the UI
   */
  fun emit(effect: MoviesEffect) {
    scope.launch {
      _effects.emit(effect)
    }
  }

  /**
   * Consume effects with a handler
   */
  fun consumeEffects(handler: suspend (MoviesEffect) -> Unit) {
    scope.launch {
      effects.collect { effect ->
        handler(effect)
      }
    }
  }
}