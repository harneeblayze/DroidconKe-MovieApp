package com.gyg.codelab.movies.mvi.transformer

import com.gyg.codelab.movies.mvi.state_manager.State

/**
 * Transforms domain state to UI-specific state.
 * This is a pure function with no side effects.
 *
 * @param S Input state type (domain state)
 * @param V Output type (UI state/view state)
 */
interface StateTransformer<in S : State, out V> {
    fun transform(state: S): V
}
