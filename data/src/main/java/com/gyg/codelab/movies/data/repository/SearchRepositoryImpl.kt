package com.gyg.codelab.movies.data.repository

import com.gyg.codelab.movies.data.api.MoviesApiService
import com.gyg.codelab.movies.data.dto.MovieDTO
import com.gyg.codelab.movies.domain.exceptions.MovieException
import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.domain.repository.SearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Implementation of SearchRepository
 * Handles search-specific API calls
 */
class SearchRepositoryImpl(
  private val apiService: MoviesApiService,
  private val apiKey: String,
  private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SearchRepository {

  override suspend fun searchMovies(query: String, page: Int): Result<List<Movie>> {
    return safeApiCall {
      withContext(ioDispatcher) {
        val response = apiService.searchMovies(apiKey, query, page)
        response.results.map { it.toDomain() }
      }
    }
  }

  /**
   * Safely executes an API call and maps exceptions to domain exceptions
   */
  private suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
    return try {
      Result.success(apiCall())
    } catch (e: Exception) {
      Result.failure(mapException(e))
    }
  }

  /**
   * Maps various exceptions to domain-specific exceptions
   */
  private fun mapException(exception: Exception): MovieException {
    return when (exception) {
      is UnknownHostException -> {
        MovieException.NetworkError(
          message = "No internet connection",
          cause = exception
        )
      }

      is SocketTimeoutException -> {
        MovieException.TimeoutError(
          message = "Connection timeout",
          cause = exception
        )
      }

      is IOException -> {
        MovieException.NetworkError(
          message = "Network error occurred",
          cause = exception
        )
      }

      is HttpException -> {
        when (exception.code()) {
          in 400..499 -> MovieException.ClientError(
            code = exception.code(),
            message = "Request failed: ${exception.message()}",
            cause = exception
          )

          in 500..599 -> MovieException.ServerError(
            message = "Server error occurred",
            cause = exception
          )

          else -> MovieException.UnknownError(
            message = "HTTP error: ${exception.code()}",
            cause = exception
          )
        }
      }

      else -> {
        MovieException.UnknownError(
          message = exception.message ?: "An unexpected error occurred",
          cause = exception
        )
      }
    }
  }

  private fun MovieDTO.toDomain(): Movie {
    return Movie(
      id = id,
      title = title,
      overview = overview,
      posterPath = posterPath,
      backdropPath = backdropPath,
      releaseDate = releaseDate,
      voteAverage = voteAverage,
      voteCount = voteCount
    )
  }
}
