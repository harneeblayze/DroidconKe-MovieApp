package com.gyg.codelab.movies.workflow.home

import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.ui.screens.MovieSection

data class HomeRendering(
    val movies: List<MovieSection>,
    val onToggleFavourite: (Movie) -> Unit,
    val onRetryLoad: () -> Unit,
)
