package com.gyg.codelab.movies.workflow.starter.search

import androidx.lifecycle.ViewModel
import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.domain.repository.FavoritesRepository
import com.gyg.codelab.movies.domain.repository.SearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * TODO: Implement SearchWorkflowViewModel using Workflow pattern
 *
 * This ViewModel should:
 * 1. Create a SearchWorkflow instance (from starter package)
 * 2. Use renderWorkflowIn() to run the workflow
 * 3. Expose searchRendering as StateFlow
 * 4. Provide derived flows for query and searchResults
 * 5. Implement performSearch() and toggleFavorite() methods
 *
 * Steps:
 * 1. First, implement SearchWorkflow in SearchWorkflow.kt
 * 2. Import SearchWorkflow and SearchRendering from this starter package
 * 3. Create SearchWorkflow instance with repositories
 * 4. Use renderWorkflowIn() with the workflow
 * 5. Map the rendering and stateIn() to get StateFlow
 * 6. Create derived flows for query and searchResults using .map()
 * 7. Implement performSearch() to call rendering.value.onQueryChange(query)
 * 8. Implement toggleFavorite() to launch coroutine with repository
 *
 * Key imports needed:
 * import com.gyg.codelab.movies.workflow.starter.search.SearchWorkflow
 * import com.gyg.codelab.movies.workflow.starter.search.SearchRendering
 * import com.squareup.workflow1.renderWorkflowIn
 * import kotlinx.coroutines.flow.map
 * import kotlinx.coroutines.flow.stateIn
 * import kotlinx.coroutines.flow.SharingStarted
 *
 * Reference: feature-workflow/src/main/java/com/gyg/codelab/movies/workflow/search/SearchWorkflowViewModel.kt
 */
class SearchWorkflowViewModel(
    private val searchRepository: SearchRepository,
    private val favoritesRepository: FavoritesRepository,
) : ViewModel() {

    // TODO: Create SearchWorkflow instance
    // private val searchWorkflow = SearchWorkflow(
    //   searchRepository = searchRepository,
    //   favoritesRepository = favoritesRepository
    // )

    // TODO: Render the workflow using renderWorkflowIn()
    // private val searchRendering: StateFlow<SearchRendering> = renderWorkflowIn(
    //   workflow = searchWorkflow,
    //   scope = viewModelScope,
    //   props = MutableStateFlow(Unit),
    //   onOutput = { }
    // )
    //   .map { it.rendering }
    //   .stateIn(
    //     scope = viewModelScope,
    //     started = SharingStarted.WhileSubscribed(5000),
    //     initialValue = SearchRendering(
    //       query = "",
    //       searchResults = emptyList(),
    //       isLoading = false,
    //       error = null,
    //       onQueryChange = {}
    //     )
    //   )

    // TODO: Expose query as derived flow
    // val query: StateFlow<String> = searchRendering
    //   .map { it.query }
    //   .stateIn(
    //     scope = viewModelScope,
    //     started = SharingStarted.WhileSubscribed(5000),
    //     initialValue = ""
    //   )

    // TODO: Expose search results as derived flow
    // val searchResults: StateFlow<List<Movie>> = searchRendering
    //   .map { it.searchResults }
    //   .stateIn(
    //     scope = viewModelScope,
    //     started = SharingStarted.WhileSubscribed(5000),
    //     initialValue = emptyList()
    //   )

    // Placeholder for now - replace with actual implementation above
    val query: StateFlow<String> = MutableStateFlow<String>("").asStateFlow()
    val searchResults: StateFlow<List<Movie>> =
        MutableStateFlow<List<Movie>>(emptyList()).asStateFlow()

    /**
     * TODO: Implement performSearch function
     * Call searchRendering.value.onQueryChange(query)
     */
    fun performSearch(query: String) {
        // TODO: searchRendering.value.onQueryChange(query)
    }

    /**
     * TODO: Implement toggleFavorite function
     * Launch coroutine to call favoritesRepository.toggleFavorite(movie)
     */
    fun toggleFavorite(movie: Movie) {
        // TODO: viewModelScope.launch { favoritesRepository.toggleFavorite(movie) }
        // NB: if you implement the workflow properly, you won't need this function
    }
}
