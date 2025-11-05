package com.gyg.codelab.movies.domain.exceptions

/**
 * Base exception for all movie-related errors
 */
sealed class MovieException(message: String, cause: Throwable? = null) : Exception(message, cause) {

  /**
   * Network-related errors
   */
  data class NetworkError(
    override val message: String = "No internet connection",
    override val cause: Throwable? = null
  ) : MovieException(message, cause)

  /**
   * Server errors (5xx)
   */
  data class ServerError(
    override val message: String = "Server error",
    override val cause: Throwable? = null
  ) : MovieException(message, cause)

  /**
   * Client errors (4xx)
   */
  data class ClientError(
    val code: Int,
    override val message: String = "Request failed",
    override val cause: Throwable? = null
  ) : MovieException(message, cause)

  /**
   * Timeout errors
   */
  data class TimeoutError(
    override val message: String = "Request timeout",
    override val cause: Throwable? = null
  ) : MovieException(message, cause)

  /**
   * Data parsing errors
   */
  data class DataError(
    override val message: String = "Invalid data",
    override val cause: Throwable? = null
  ) : MovieException(message, cause)

  /**
   * Unknown errors
   */
  data class UnknownError(
    override val message: String = "An unexpected error occurred",
    override val cause: Throwable? = null
  ) : MovieException(message, cause)
}