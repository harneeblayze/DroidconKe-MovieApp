package com.gyg.codelab.movies.domain.repository

import com.gyg.codelab.movies.domain.model.Movie

/**
 * Repository interface for search functionality
 * Separates search concerns from general movie fetching
 */
interface SearchRepository {
  suspend fun searchMovies(query: String, page: Int = 1): Result<List<Movie>>
}
