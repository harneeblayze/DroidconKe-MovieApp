package com.gyg.codelab.movies.data.repository

import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

/**
 * In-memory implementation of FavoritesRepository
 * In a real app, this would use Room or DataStore for persistence
 */
class FavoritesRepositoryImpl : FavoritesRepository {

  private val favoriteMovies = MutableStateFlow<Map<Int, Movie>>(emptyMap())

  override fun getFavoriteMovies(): Flow<List<Movie>> {
    return favoriteMovies.map { it.values.toList() }
  }

  override fun isFavorite(movieId: Int): Flow<Boolean> {
    return favoriteMovies.map { it.containsKey(movieId) }
  }

  override suspend fun toggleFavorite(movie: Movie) {
    val currentFavorites = favoriteMovies.value.toMutableMap()
    if (currentFavorites.containsKey(movie.id)) {
      currentFavorites.remove(movie.id)
    } else {
      currentFavorites[movie.id] = movie
    }
    favoriteMovies.value = currentFavorites
  }

  override suspend fun addFavorite(movie: Movie) {
    val currentFavorites = favoriteMovies.value.toMutableMap()
    currentFavorites[movie.id] = movie
    favoriteMovies.value = currentFavorites
  }

  override suspend fun removeFavorite(movieId: Int) {
    val currentFavorites = favoriteMovies.value.toMutableMap()
    currentFavorites.remove(movieId)
    favoriteMovies.value = currentFavorites
  }
}
