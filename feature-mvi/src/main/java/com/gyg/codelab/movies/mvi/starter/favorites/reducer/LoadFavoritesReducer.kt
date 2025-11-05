package com.gyg.codelab.movies.mvi.starter.favorites.reducer

/**
 * TODO: Implement LoadFavoritesReducer
 *
 * This reducer should:
 * 1. Observe the favorites repository flow
 * 2. Publish SetFavorites events when favorites change
 *
 * Pattern (similar to LoadAllMoviesReducer but simpler):
 * - Launch a coroutine that collects from favoritesRepository.getFavoriteMovies()
 * - On each emission, publish SetFavorites event
 *
 * Example structure:
 * class LoadFavoritesReducer(
 *   private val favoritesRepository: FavoritesRepository,
 *   private val scope: CoroutineScope,
 *   private val publishEvent: (FavoritesEvent) -> Unit
 * ) : Reducer<FavoritesState, FavoritesEvent.LoadFavorites> {
 *
 *   private var observingJob: Job? = null
 *
 *   override suspend fun reduce(
 *     state: FavoritesState,
 *     event: FavoritesEvent.LoadFavorites
 *   ): FavoritesState {
 *     if (observingJob == null) {
 *       observingJob = scope.launch {
 *         favoritesRepository.getFavoriteMovies().collect { favorites ->
 *           publishEvent(FavoritesEvent.SetFavorites(favorites))
 *         }
 *       }
 *     }
 *     return state.copy(isLoading = true)
 *   }
 * }
 *
 * Reference: feature-mvi/src/main/java/com/gyg/codelab/movies/mvi/starter/home/reducer/LoadAllMoviesReducer.kt
 */

// TODO: Implement LoadFavoritesReducer class here
