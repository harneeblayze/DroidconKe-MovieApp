package com.gyg.codelab.movies.domain.repository

import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.domain.model.MovieCategory
import com.gyg.codelab.movies.domain.model.MovieDetail

/**
 * Repository for fetching movies by category
 * Focused on movie catalog operations only
 */
interface MoviesRepository {
  // Generic method for fetching movies by category
  suspend fun getMovies(category: MovieCategory, page: Int = 1): Result<List<Movie>>

  // Legacy methods - can be deprecated later
  suspend fun getPopularMovies(page: Int = 1): Result<List<Movie>>
  suspend fun getTrendingMovies(page: Int = 1): Result<List<Movie>>
  suspend fun getUpcomingMovies(page: Int = 1): Result<List<Movie>>
  suspend fun getTopRatedMovies(page: Int = 1): Result<List<Movie>>
  suspend fun getNowPlayingMovies(page: Int = 1): Result<List<Movie>>

  suspend fun getMovieDetail(movieId: Int): Result<MovieDetail>
}
