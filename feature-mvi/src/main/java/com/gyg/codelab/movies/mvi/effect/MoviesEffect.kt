package com.gyg.codelab.movies.mvi2.effect

import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.mvi2.state_manager.Effect

/**
 * Represents side effects that don't change the state
 * but trigger external actions (navigation, toasts, etc.)
 */
sealed class MoviesEffect : Effect {
  data class ShowError(val message: String) : MoviesEffect()
  data class NavigateToDetails(val movie: Movie) : MoviesEffect()
  data class ShowToast(val message: String) : MoviesEffect()
  object NavigateToSearch : MoviesEffect()
}