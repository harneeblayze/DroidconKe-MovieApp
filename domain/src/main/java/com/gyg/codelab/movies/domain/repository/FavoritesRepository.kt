package com.gyg.codelab.movies.domain.repository

import com.gyg.codelab.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing favorite movies
 * Handles local persistence of user's favorite movies
 */
interface FavoritesRepository {
  /**
   * Get all favorite movies as a flow
   */
  fun getFavoriteMovies(): Flow<List<Movie>>

  /**
   * Check if a movie is favorited
   */
  fun isFavorite(movieId: Int): Flow<Boolean>

  /**
   * Toggle favorite status for a movie
   */
  suspend fun toggleFavorite(movie: Movie)

  /**
   * Add a movie to favorites
   */
  suspend fun addFavorite(movie: Movie)

  /**
   * Remove a movie from favorites
   */
  suspend fun removeFavorite(movieId: Int)
}
