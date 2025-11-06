package com.gyg.codelab.movies.mvi.starter.home

import com.gyg.codelab.movies.domain.model.MovieCategory
import com.gyg.codelab.movies.mvi.transformer.StateTransformer
import com.gyg.codelab.movies.ui.screens.MovieSection
import kotlinx.coroutines.flow.flowOf

/**
 * Transforms Home domain state to UI state
 * Separates business logic from UI representation
 * Converts CategoryState into MovieSection for UI consumption
 */
class HomeTransformer : StateTransformer<HomeState, HomeUIState> {

    override fun transform(state: HomeState): HomeUIState {
        return HomeUIState(
            movieSections = transformToMovieSections(state),
        )
    }

    /**
     * Transform state to MovieSection list for LazyMoviesScreen
     * Each section has its own loading and error state from its CategoryState
     */
    private fun transformToMovieSections(state: HomeState): List<MovieSection> {
        return buildList {
            // Trending Carousel section
            val trending = state.movies[MovieCategory.TRENDING] ?: CategoryState.Loading
            add(
                MovieSection.Carousel(
                    category = MovieCategory.TRENDING,
                    movies = flowOf(trending.moviesOrEmpty()),
                    error = trending.errorOrNull(),
                ),
            )

            // Now Playing section
            val nowPlaying = state.movies[MovieCategory.NOW_PLAYING] ?: CategoryState.Loading
            add(
                MovieSection.Regular(
                    category = MovieCategory.NOW_PLAYING,
                    movies = flowOf(nowPlaying.moviesOrEmpty()),
                    title = "Now Playing",
                    isLoading = nowPlaying.isLoading(),
                    error = nowPlaying.errorOrNull(),
                ),
            )

            // Popular section
            val popular = state.movies[MovieCategory.POPULAR] ?: CategoryState.Loading
            add(
                MovieSection.Regular(
                    category = MovieCategory.POPULAR,
                    movies = flowOf(popular.moviesOrEmpty()),
                    title = "Popular",
                    isLoading = popular.isLoading(),
                    error = popular.errorOrNull(),
                ),
            )

            // Top Rated section
            val topRated = state.movies[MovieCategory.TOP_RATED] ?: CategoryState.Loading
            add(
                MovieSection.Regular(
                    category = MovieCategory.TOP_RATED,
                    movies = flowOf(topRated.moviesOrEmpty()),
                    title = "Top Rated",
                    isLoading = topRated.isLoading(),
                    error = topRated.errorOrNull(),
                ),
            )

            // Upcoming section
            val upcoming = state.movies[MovieCategory.UPCOMING] ?: CategoryState.Loading
            add(
                MovieSection.Regular(
                    category = MovieCategory.UPCOMING,
                    movies = flowOf(upcoming.moviesOrEmpty()),
                    title = "Upcoming",
                    isLoading = upcoming.isLoading(),
                    error = upcoming.errorOrNull(),
                ),
            )
        }
    }
}
