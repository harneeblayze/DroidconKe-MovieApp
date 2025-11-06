package com.gyg.codelab.movies.mvi.state_manager

/**
 * Marker interface for side effects.
 * Effects represent one-time events like navigation, showing toasts, etc.
 * Unlike State, effects are not persisted and are consumed once.
 */
interface Effect
