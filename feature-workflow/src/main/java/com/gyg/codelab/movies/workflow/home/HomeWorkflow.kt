package com.gyg.codelab.movies.workflow.home

import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.domain.model.MovieCategory
import com.gyg.codelab.movies.domain.repository.FavoritesRepository
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.renderChild
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class HomeWorkflow(
    private val movieCategoryWorkflow: MovieCategoryWorkflow,
    private val viewModelScope: CoroutineScope,
    private val favoritesRepository: FavoritesRepository,
) : StatefulWorkflow<Unit, HomeState, Unit, HomeRendering>() {

    private val RenderContext<Unit, HomeState, Unit>.toggleFavourite: (Movie) -> Unit
        get() = eventHandler("toggle_favorite", remember = true) { movie ->
            viewModelScope.launch {
                favoritesRepository.toggleFavorite(movie)
            }
        }

    private val RenderContext<Unit, HomeState, Unit>.retryLoad: () -> Unit
        get() = eventHandler("retry_load", remember = true) {
            state = state.copy(loadNumber = state.loadNumber + 1)
        }

    override fun initialState(props: Unit, snapshot: Snapshot?): HomeState {
        return HomeState(loadNumber = 0)
    }

    override fun render(
        renderProps: Unit,
        renderState: HomeState,
        context: RenderContext<Unit, HomeState, Unit>,
    ): HomeRendering {
        return HomeRendering(
            movies = MovieCategory.entries.map { category ->
                context.renderChild(
                    movieCategoryWorkflow,
                    MovieCategoryProps(category, renderState.loadNumber),
                    key = "category-${category.name}",
                )
            },
            onToggleFavourite = context.toggleFavourite,
            onRetryLoad = context.retryLoad,
        )
    }

    override fun snapshotState(state: HomeState): Snapshot? = null
}

data class HomeState(
    val loadNumber: Long,
)
