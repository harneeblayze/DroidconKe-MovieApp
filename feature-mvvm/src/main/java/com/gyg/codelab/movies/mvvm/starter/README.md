# MVVM Starter Package

Welcome to the MVVM workshop! This package contains starter code for implementing movie features
using the Model-View-ViewModel pattern.

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

- **HomeViewModel**: Managing movie categories, loading states, and error handling
- **HomeRoute**: Composable UI that connects ViewModel to the screen
- **Reactive Updates**: Using `combine()` to automatically update favorite status
- **Lazy Loading**: Loading movies only when needed
- **Error Handling**: Comprehensive error management with retry logic

### Key Patterns in Home:

1. **State Management**: Multiple `StateFlow`s for different categories
2. **Reactive Favorites**: `combine()` operator merges movies with favorites automatically
3. **Loading States**: Per-category loading and error states
4. **ViewModel Scope**: All coroutines run in `viewModelScope`

## 🔨 Your Tasks

### Task 1: Implement Search Feature

**Location**: `search/` package

**Files to Complete**:

- `SearchViewModel.kt` - Implement search logic with reactive favorites
- `SearchRoute.kt` - Connect ViewModel to UI

**Requirements**:

1. Implement `searchMovies(query: String)` function
2. Use `combine()` to merge search results with favorites
3. Expose `searchResults` as `StateFlow<List<Movie>>`
4. Expose `searchQuery` as `StateFlow<String>`
5. Implement `toggleFavorite(movie: Movie)` function
6. Connect everything in `SearchRoute`

**Hints**:

- Look at `HomeViewModel` for inspiration on using `combine()`
- Use `searchRepository.searchMovies(query)` for searching
- Use `favoritesRepository.getFavoriteMovies()` for favorites
- Remember to launch coroutines in `viewModelScope`

### Task 2: Implement Favorites Feature

**Location**: `favorites/` package

**Files to Complete**:

- `FavoritesViewModel.kt` - Manage favorite movies list
- `FavoritesRoute.kt` - Connect ViewModel to UI

**Requirements**:

1. Get favorites flow from `favoritesRepository.getFavoriteMovies()`
2. Expose it as `favoriteMovies: Flow<List<Movie>>`
3. Implement `toggleFavorite(movie: Movie)` function
4. Connect everything in `FavoritesRoute`

**Hints**:

- This is the simplest feature - just expose the repository flow!
- Use `collectAsStateWithLifecycle()` in the Route
- Pass `initialValue = emptyList()` when collecting

## 📚 Reference Implementation

Study the **home** package to understand:

1. **How to structure a ViewModel**:
   ```kotlin
   class HomeViewModel(
     private val moviesRepository: MoviesRepository,
     private val favoritesRepository: FavoritesRepository
   ) : ViewModel()
   ```

2. **How to use combine() for reactive updates**:
   ```kotlin
   combine(
     baseFlow,
     favoritesRepository.getFavoriteMovies()
   ) { movies, favorites ->
     // Apply favorite status
   }
   ```

3. **How to manage loading states**:
   ```kotlin
   private val categoryLoadingStates = mutableMapOf<MovieCategory, MutableStateFlow<Boolean>>()
   ```

4. **How to connect ViewModel to UI**:
   ```kotlin
   @Composable
   fun HomeRoute(viewModel: HomeViewModel, ...) {
     val movieCategories = viewModel.getAllMovieCategories()
     // Use in UI
   }
   ```

## 🎯 Success Criteria

Your implementation is complete when:

- ✅ Search feature can search for movies by query
- ✅ Search results show correct favorite status
- ✅ Favorites feature displays all favorited movies
- ✅ Toggling favorites works in both search and favorites screens
- ✅ Changes to favorites are reflected immediately (reactive)
- ✅ No manual refresh needed anywhere

## 🔗 Additional Resources

- Full solution: `feature-mvvm/src/main/java/com/gyg/codelab/movies/mvvm/` (without `starter/`)
- UI components: `ui-common/src/main/java/com/gyg/codelab/movies/ui/screens/`
- Domain models: `domain/src/main/java/com/gyg/codelab/movies/domain/`

Good luck! 🚀
