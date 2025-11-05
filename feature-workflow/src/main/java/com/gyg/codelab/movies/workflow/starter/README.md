# Workflow Starter Package

Welcome to the Workflow workshop! This package contains starter code for implementing movie features
using Square's Workflow library for state machine-based architecture.

## 📂 Package Structure

```
starter/
├── home/          ✅ COMPLETE - Reference implementation
├── search/        🔨 TODO - Implement search functionality
└── favorites/     🔨 TODO - Implement favorites functionality
```

## ✅ Home Feature (COMPLETE)

The `home` package contains a **complete implementation** that you can use as a reference. It
demonstrates:

### Components:

- **HomeWorkflowViewModel.kt**: ViewModel that runs the workflow
    - Creates `HomeWorkflow` and `MovieCategoryWorkflow` instances
    - Uses `renderWorkflowIn()` to run the workflow
    - Exposes `HomeRendering` as StateFlow

- **HomeWorkflowRoute.kt**: Composable UI that connects to workflow
    - Collects rendering from ViewModel
    - Passes data to `LazyMoviesScreen`

- **Uses existing workflows from main package**:
    - `HomeWorkflow` - Orchestrates multiple category workflows
    - `MovieCategoryWorkflow` - Handles individual category loading
    - `HomeRendering` - Output for UI consumption

### Key Workflow Patterns:

1. **State Machine**: Workflow manages state transitions
2. **Composition**: HomeWorkflow composes multiple MovieCategoryWorkflow
3. **Workers**: Async operations run as Workers
4. **Rendering**: Output type that UI consumes
5. **Reactive Favorites**: `combine()` in workers merges movies with favorites
6. **Event Handlers**: Actions defined in workflow context

## 🔨 Your Tasks

### Task 1: Implement Search Feature

**Location**: `search/` package

**Components to Create**:

1. **SearchWorkflow** (StatefulWorkflow):
   ```kotlin
   class SearchWorkflow(
     private val searchRepository: SearchRepository,
     private val favoritesRepository: FavoritesRepository
   ) : StatefulWorkflow<SearchProps, SearchState, Nothing, SearchRendering>()
   ```

2. **SearchState**:
   ```kotlin
   data class SearchState(
     val query: String = "",
     val results: List<Movie> = emptyList(),
     val isLoading: Boolean = false
   )
   ```

3. **SearchRendering**:
   ```kotlin
   data class SearchRendering(
     val query: String,
     val results: List<Movie>,
     val isLoading: Boolean,
     val onQueryChange: (String) -> Unit,
     val onToggleFavorite: (Movie) -> Unit
   )
   ```

4. **SearchWorkflowViewModel**:
    - Create SearchWorkflow instance
    - Use `renderWorkflowIn()` to run it
    - Expose rendering as StateFlow

5. **SearchWorkflowRoute**:
    - Collect rendering from ViewModel
    - Connect to SearchScreen

**Key Concepts**:

- Use `runningWorker` for search operations
- Use `combine()` in worker to merge results with favorites
- Define event handlers in render context
- Return SearchRendering from render()

**Example Worker Pattern**:

```kotlin
runningWorker(
  worker = combine(
    favoritesRepository.getFavoriteMovies(),
    searchRepository.searchMovies(query).asFlow()
  ) { favorites, moviesResult ->
    // Apply favorite status
  }.asWorker(),
  key = "search_$query"
) { result ->
  action("search_complete") {
    state = state.copy(results = result)
  }
}
```

### Task 2: Implement Favorites Feature

**Location**: `favorites/` package

**Components to Create**:

1. **FavoritesWorkflow** (StatelessWorkflow or StatefulWorkflow):
   ```kotlin
   class FavoritesWorkflow(
     private val favoritesRepository: FavoritesRepository
   ) : StatefulWorkflow<Unit, FavoritesState, Nothing, FavoritesRendering>()
   ```

2. **FavoritesState**:
   ```kotlin
   data class FavoritesState(
     val favorites: List<Movie> = emptyList()
   )
   ```

3. **FavoritesRendering**:
   ```kotlin
   data class FavoritesRendering(
     val favorites: List<Movie>,
     val onToggleFavorite: (Movie) -> Unit
   )
   ```

4. **FavoritesWorkflowViewModel**:
    - Create FavoritesWorkflow instance
    - Use `renderWorkflowIn()` to run it
    - Expose rendering as StateFlow

5. **FavoritesWorkflowRoute**:
    - Collect rendering from ViewModel
    - Connect to FavoritesScreen

**Key Concepts**:

- Use `runningWorker` to observe favorites repository
- Define toggle favorite event handler
- Return FavoritesRendering from render()

## 📚 Reference Implementation

Study the **home** package and existing workflows to understand:

1. **How to create a ViewModel with Workflow**:
   ```kotlin
   class HomeWorkflowViewModel(
     moviesRepository: MoviesRepository,
     favoritesRepository: FavoritesRepository
   ) : ViewModel() {
     private val homeWorkflow = HomeWorkflow(...)
     
     val homeRendering: StateFlow<HomeRendering> = renderWorkflowIn(
       workflow = homeWorkflow,
       scope = viewModelScope,
       props = MutableStateFlow(Unit),
       onOutput = { }
     ).map { it.rendering }.stateIn(...)
   }
   ```

2. **How workflows compose** (See `HomeWorkflow.kt`):
   ```kotlin
   override fun render(...): HomeRendering {
     return HomeRendering(
       movies = MovieCategory.entries.map { category ->
         context.renderChild(movieCategoryWorkflow, props, key)
       }
     )
   }
   ```

3. **How workers perform async operations** (See `MovieCategoryWorkflow.kt`):
   ```kotlin
   runningWorker(
     worker = combine(
       favoritesRepository.getFavoriteMovies(),
       flow { emit(repository.getMovies(category)) }
     ) { favorites, moviesResult ->
       // Process and return result
     }.asWorker(),
     key = "loading_movies_$category"
   ) { result ->
     action("loaded_movies") {
       state = Loaded(movies = result)
     }
   }
   ```

4. **How to define event handlers**:
   ```kotlin
   private val RenderContext<...>.toggleFavorite: (Movie) -> Unit
     get() = eventHandler("toggle_favorite", remember = true) { movie ->
       viewModelScope.launch {
         favoritesRepository.toggleFavorite(movie)
       }
     }
   ```

5. **How to connect to UI**:
   ```kotlin
   @Composable
   fun HomeWorkflowRoute(viewModel: HomeWorkflowViewModel, ...) {
     val rendering by viewModel.homeRendering.collectAsStateWithLifecycle()
     
     LazyMoviesScreen(
       movieSections = rendering.movies,
       onToggleFavorite = rendering.onToggleFavourite
     )
   }
   ```

## 🎯 Success Criteria

Your implementation is complete when:

- ✅ Search workflow manages search state
- ✅ Workers handle async operations
- ✅ Reactive favorites using `combine()`
- ✅ Event handlers defined in workflow context
- ✅ Rendering exposed to UI
- ✅ Changes reflected immediately without manual refresh
- ✅ Proper state machine transitions

## 🔗 Additional Resources

- Full solution: `feature-workflow/src/main/java/com/gyg/codelab/movies/workflow/` (without
  `starter/`)
- Existing workflows: `feature-workflow/src/main/java/com/gyg/codelab/movies/workflow/home/`
- Workflow library docs: [Square Workflow](https://square.github.io/workflow/)
- UI components: `ui-common/src/main/java/com/gyg/codelab/movies/ui/screens/`

Good luck! 🚀
