package com.gyg.codelab.movies.mvi2.state_manager


import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import org.koin.core.scope.Scope
import kotlin.reflect.KClass

/**
 * Marker interface for all state objects in MVI
 */
interface State

/**
 * Marker interface for all events (user actions/system events)
 */
interface Event

/**
 * A Reducer is a component that receives an [Event] and a [State] from [StateManager],
 * processes it and returns a new [State].
 *
 * For operations with side effects (like API calls), the reducer can:
 * 1. Return the current/updated state immediately
 * 2. Launch coroutines that eventually publish new events with results
 */
fun interface Reducer<S : State, in E : Event> {
  suspend fun reduce(state: S, event: E): S
}

/**
 * Manages state and processes events through reducers
 */
interface StateManager<S : State, E : Event> {
  fun publishEvent(event: E)
  fun getState(): StateFlow<S>
  fun startProcessingEvents()
}

/**
 * Simple StateManager implementation for MVI
 * - Maintains current state in StateFlow
 * - Processes events through registered reducers
 * - Uses Koin scope to resolve reducers dynamically
 */
class StateManagerImpl<S : State, E : Event>(
  initialState: S,
  private val scope: Scope,
  private val lazyViewModelScope: () -> CoroutineScope,
) : StateManager<S, E> {

  private val stateFlow = MutableStateFlow(initialState)
  private val eventFlow = MutableSharedFlow<E>()
  private var started = false

  private val viewModelScope by lazy(lazyViewModelScope)

  override fun getState(): StateFlow<S> = stateFlow

  override fun publishEvent(event: E) {

      viewModelScope.launch {
        eventFlow.emit(event)
      }
  }

  override fun startProcessingEvents() {
    if (started) return
    started = true

    viewModelScope.launch {
      eventFlow.collect { event ->
        val currentState = stateFlow.value
        val reducer = findReducer(currentState::class, event::class)

        reducer?.let {
          val newState = it.reduce(currentState, event)
          stateFlow.value = newState
        }
      }
    }
  }

  @Suppress("UNCHECKED_CAST")
  private fun findReducer(
    stateClass: KClass<out S>,
    eventClass: KClass<out E>
  ): Reducer<S, E>? {
    return scope.getReducer(stateClass, eventClass) as? Reducer<S, E>
  }
}
