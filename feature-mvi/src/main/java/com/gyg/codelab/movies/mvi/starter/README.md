# MVI Starter Package

Welcome to the MVI workshop! This package contains starter code for implementing movie features
using the Model-View-Intent pattern with State Management.

## 📂 Package Structure

```
starter/
├── home/          ✅ COMPLETE - Reference implementation
│   ├── reducer/   - All reducers for home events
│   └── ...
├── search/        🔨 TODO - Implement search functionality
│   └── reducer/   - Starter files with TODOs
│       ├── PerformSearchReducer.kt       🔨 TODO
│       ├── ClearSearchReducer.kt         🔨 TODO
│       ├── ObserveSearchFavoritesReducer.kt  🔨 TODO
│       ├── SetSearchingReducer.kt        🔨 TODO
│       ├── SetSearchErrorReducer.kt      🔨 TODO
│       ├── ClearSearchErrorReducer.kt    🔨 TODO
│       └── SetSearchFavoriteMoviesReducer.kt 🔨 TODO
└── favorites/     🔨 TODO - Implement favorites functionality
```

## ✅ Home Feature (COMPLETE)

The `home` package contains a **complete implementation** demonstrating MVI architecture:

### Components:

- **HomeState.kt**: Domain state and UI state definitions
    - `CategoryState` - Sealed class for Loading/Success/Error per category
    - `HomeState` - Domain state with map of movie categories
    - `HomeUIState` - UI-ready state with MovieSections

- **HomeEvent.kt**: All possible events (user actions & system events)
    - `LoadAllMovies` - Trigger loading
    - `SetCategoryMovies` - Update state with loaded movies
    - `ToggleFavorite` - Toggle favorite status
    - `Refresh` - Refresh all data

- **HomeTransformer.kt**: Transforms domain state to UI state
    - Converts `HomeState` to `HomeUIState`
    - Creates `MovieSection` list for UI

- **HomeMviViewModel.kt**: ViewModel managing state and events
    - Uses `StateManager` for state management
    - Transforms state for UI consumption
    - Handles event dispatching

- **Reducers**: Pure functions that update state
    - `LoadAllMoviesReducer` - Loads movies with reactive favorites
    - `SetCategoryMoviesReducer` - Updates state with movies
    - `SetCategoryErrorReducer` - Updates state with errors
    - `ToggleHomeFavoriteReducer` - Handles favorite toggling
    - `RefreshHomeReducer` - Triggers refresh

- **HomeMviModule.kt**: Koin dependency injection module
    - Registers StateManager, reducers, transformer, ViewModel

### Key MVI Patterns:

1. **Unidirectional Data Flow**: Events → Reducers → State → UI
2. **Immutable State**: State objects are immutable (data classes)
3. **Reducers**: Pure functions that take (State, Event) and return new State
4. **State Manager**: Central hub managing state and event processing
5. **Reactive Favorites**: `combine()` in reducers merges movies with favorites

## 🔨 Your Tasks

### Task 1: Implement Search Feature

**Location**: `search/` package

**Components to Create**:

1. **SearchState.kt**:
   ```kotlin
   data class SearchState(
     val query: String = "",
     val movies: List<Movie> = emptyList(),
     val favoriteMovies: List<Movie> = emptyList(),
     val isSearching: Boolean = false,
     val error: String? = null
   ) : State
   ```

2. **SearchEvent.kt**:
   ```kotlin
   sealed class SearchEvent : Event {
     data class PerformSearch(val query: String) : SearchEvent()
     object ClearSearch : SearchEvent()
     object ObserveSearchFavorites : SearchEvent()
     data class SetSearching(val isSearching: Boolean) : SearchEvent()
     data class SetSearchError(val error: String) : SearchEvent()
     object ClearSearchError : SearchEvent()
     data class SetSearchFavoriteMovies(val movies: List<Movie>) : SearchEvent()
     data class ToggleSearchFavorite(val movie: Movie) : SearchEvent()
   }
   ```

3. **Reducers** (7 reducers to implement):
    - ✅ **PerformSearchReducer.kt** (starter with TODOs)
        - Performs the actual search using `SearchRepository`
        - Emits `SetSearching(true)` before search
        - Emits `SetSearching(false)` after search
        - Emits `SetSearchError` on failure
        - Combines search results with favorites

   - ✅ **ClearSearchReducer.kt** (starter with TODOs)
       - Resets search state to initial values
       - Clears query, movies, and errors
       - Used when user clears search input

   - ✅ **ObserveSearchFavoritesReducer.kt** (starter with TODOs)
       - Observes `FavoritesRepository.observeFavorites()`
       - Emits `SetSearchFavoriteMovies` with updated favorites
       - Ensures reactive updates when favorites change

   - ✅ **SetSearchingReducer.kt** (starter with TODOs)
       - Simple state update for loading indicator
       - Sets `isSearching` flag in state

   - ✅ **SetSearchErrorReducer.kt** (starter with TODOs)
       - Updates state with error message
       - Used when search fails

   - ✅ **ClearSearchErrorReducer.kt** (starter with TODOs)
       - Clears error from state
       - Used when user retries or dismisses error

   - ✅ **SetSearchFavoriteMoviesReducer.kt** (starter with TODOs)
       - Updates `favoriteMovies` in state
       - Used by `ObserveSearchFavoritesReducer`

   - ⚠️ **ToggleSearchFavoriteReducer.kt** (implement yourself)
       - Toggles favorite status for a movie
       - Pattern: check if movie is favorite, then add/remove
       - Use `FavoritesRepository.addFavorite()` or `.removeFavorite()`

**Implementation Guide**:

All starter reducer files have been created with detailed TODOs and hints. Open each file to see:

- The exact function signature you need
- Step-by-step TODO comments
- Hints about what each step should do
- Expected return values

**Implementation Order** (recommended):

1. **Start with simple sync reducers** (easiest):
    - `SetSearchingReducer` - Just copy `isSearching` into state
    - `SetSearchErrorReducer` - Just copy `error` into state
    - `ClearSearchErrorReducer` - Set error to null
    - `SetSearchFavoriteMoviesReducer` - Copy `favoriteMovies` into state
    - `ClearSearchReducer` - Reset all fields to initial values

2. **Then implement async reducers** (medium):
    - `ObserveSearchFavoritesReducer` - Collect from Flow and emit events
    - `PerformSearchReducer` - Call repository and emit multiple events

3. **Finally implement toggle** (you choose the approach):
    - `ToggleSearchFavoriteReducer` - Check favorites, then add/remove

**Pattern Examples**:

```kotlin
// SYNC REDUCER - Direct state update
class SetSearchingReducer : Reducer<SearchState, SearchEvent.SetSearching> {
  override suspend fun reduce(state: SearchState, event: SearchEvent.SetSearching): SearchState {
    return state.copy(isSearching = event.isSearching)
  }
}

// ASYNC REDUCER - Emit events, return unchanged state
class PerformSearchReducer(...) : Reducer<SearchState, SearchEvent.PerformSearch> {
  override suspend fun reduce(state: SearchState, event: SearchEvent.PerformSearch): SearchState {
    // 1. Emit loading event
    stateManager.publish(SearchEvent.SetSearching(true))
    
    // 2. Perform async operation
    val result = searchRepository.searchMovies(event.query)
    
    // 3. Emit result events
    result.onSuccess { movies ->
      // Combine with favorites...
    }.onFailure { error ->
      stateManager.publish(SearchEvent.SetSearchError(error.message ?: "Error"))
    }
    
    // 4. Emit done loading
    stateManager.publish(SearchEvent.SetSearching(false))
    
    return state // State changes happen through emitted events!
  }
}

// FLOW OBSERVER - Collect and emit events
class ObserveSearchFavoritesReducer(...) : Reducer<SearchState, SearchEvent.ObserveSearchFavorites> {
  override suspend fun reduce(state: SearchState, event: SearchEvent.ObserveSearchFavorites): SearchState {
    favoritesRepository.observeFavorites()
      .collect { favorites ->
        stateManager.publish(SearchEvent.SetSearchFavoriteMovies(favorites))
      }
    return state
  }
}
```

**Key MVI Principles**:

1. **Sync Reducers**: Return new state directly with `.copy()`
2. **Async Reducers**: Emit multiple events via `stateManager.publish()`, return state unchanged
3. **Flow Observers**: Collect from Flow, emit events for each emission
4. **State Immutability**: NEVER mutate state, always create new instances
5. **Single Responsibility**: Each reducer handles ONE event type

4. **SearchTransformer.kt** (optional) - Transform domain to UI state if needed
5. **SearchMviViewModel.kt** - Wire everything together
6. **SearchMviRoute.kt** - Connect to UI
7. **SearchMviModule.kt** - Koin module for DI

**Key Concepts**:

- Use `StateManager<SearchState, SearchEvent>`
- Implement reducers as separate classes
- Use `combine()` in search reducer for reactive favorites
- Register all components in Koin module

### Task 2: Implement Favorites Feature

**Location**: `favorites/` package

**Components to Create**:

1. **FavoritesState.kt**:
   ```kotlin
   data class FavoritesState(
     val favorites: List<Movie> = emptyList(),
     val isLoading: Boolean = false
   ) : State
   ```

2. **FavoritesEvent.kt**:
   ```kotlin
   sealed class FavoritesEvent : Event {
     object LoadFavorites : FavoritesEvent()
     data class SetFavorites(val movies: List<Movie>) : FavoritesEvent()
     data class ToggleFavorite(val movie: Movie) : FavoritesEvent()
   }
   ```

3. **Reducers**:
    - **LoadFavoritesReducer** - Observe favorites repository
    - **SetFavoritesReducer** - Update state with favorites
    - **ToggleFavoriteReducer** - Handle favorite toggling

4. **FavoritesMviViewModel.kt** - Wire everything together
5. **FavoritesMviRoute.kt** - Connect to UI
6. **FavoritesMviModule.kt** - Koin module for DI

## 📚 Reference Implementation

Study the **home** package to understand:

1. **How to structure State**:
   ```kotlin
   data class HomeState(
     val movies: Map<MovieCategory, CategoryState> = emptyMap()
   ) : State
   ```

2. **How to define Events**:
   ```kotlin
   sealed class HomeEvent : Event {
     object LoadAllMovies : HomeEvent()
     data class SetCategoryMovies(...) : HomeEvent()
   }
   ```

3. **How to create Reducers**:
   ```kotlin
   class LoadAllMoviesReducer(...) : Reducer<HomeState, HomeEvent.LoadAllMovies> {
     override suspend fun reduce(state: HomeState, event: HomeEvent.LoadAllMovies): HomeState {
       // Logic here
     }
   }
   ```

4. **How to wire with StateManager**:
   ```kotlin
   class HomeMviViewModel(
     private val stateManager: StateManager<HomeState, HomeEvent>,
     private val transformer: HomeTransformer
   ) : ViewModel()
   ```

5. **How to setup Koin module**:
   ```kotlin
   scope(named(CONTAINER_NAME)) {
     scoped<StateManager<*, *>> { StateManagerImpl(...) }
     scopedReducer<HomeState, HomeEvent.LoadAllMovies> { LoadAllMoviesReducer(...) }
   }
   ```

## 🎯 Success Criteria

Your implementation is complete when:

- ✅ All state is immutable (data classes)
- ✅ Events represent all user actions
- ✅ Reducers are pure functions
- ✅ StateManager handles all state updates
- ✅ Reactive favorites using `combine()`
- ✅ Proper Koin module setup
- ✅ Changes reflected immediately without manual refresh

## 🔗 Additional Resources

- Full solution: `feature-mvi/src/main/java/com/gyg/codelab/movies/mvi/` (without `starter/`)
- StateManager: `feature-mvi/src/main/java/com/gyg/codelab/movies/mvi/state_manager/`
- UI components: `ui-common/src/main/java/com/gyg/codelab/movies/ui/screens/`

Good luck! 🚀
