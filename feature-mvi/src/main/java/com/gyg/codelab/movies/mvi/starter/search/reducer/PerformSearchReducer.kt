package com.gyg.codelab.movies.mvi.starter.search.reducer

/**
 * TODO: Implement PerformSearchReducer
 *
 * This is the MAIN search reducer that handles debouncing and performing the search.
 *
 * KEY RESPONSIBILITIES:
 * 1. Debounce search queries (wait 300ms after user stops typing)
 * 2. Filter out blank queries
 * 3. Avoid duplicate searches for the same query
 * 4. Call searchRepository to perform the search
 * 5. Publish events for search results or errors
 * 6. Manage the searching state
 *
 * ARCHITECTURE PATTERN:
 * - Use a Channel to queue search queries
 * - Use Flow operators for debouncing and deduplication
 * - Publish multiple events: SetSearching, SetSearchResults/SetSearchError
 *
 * IMPLEMENTATION STEPS:
 *
 * Step 1: Create a Channel<String> for queries
 *   private val queryChannel = Channel<String>(Channel.CONFLATED)
 *   - CONFLATED means only the latest value is kept if channel is full
 *   - This ensures we don't process old search queries
 *
 * Step 2: In reduce(), send query to channel
 *   - If query is empty → publish ClearSearch event
 *   - Otherwise → queryChannel.trySend(query)
 *   - Update state with new query
 *
 * Step 3: Set up debounced search pipeline (only once)
 *   queryChannel.receiveAsFlow()
 *     .filter { it.isNotBlank() }        // Skip blank queries
 *     .distinctUntilChanged()             // Skip duplicate consecutive queries
 *     .debounce(300)                      // Wait 300ms after last emission
 *     .flatMapLatest { query ->           // Cancel previous search if new one starts
 *       flow {
 *         emit(SearchEvent.SetSearching(true))
 *
 *         val result = searchRepository.searchMovies(query)
 *         result.fold(
 *           onSuccess = { emit(SearchEvent.SetSearchResults(it)) },
 *           onFailure = { emit(SearchEvent.SetSearchError(it.message ?: "Error")) }
 *         )
 *
 *         emit(SearchEvent.SetSearching(false))
 *       }
 *     }
 *     .onEach { publishEvent(it) }       // Publish each event
 *     .launchIn(scope)                    // Start collecting in scope
 *
 * FLOW OPERATORS EXPLAINED:
 *
 * - filter { it.isNotBlank() }
 *   Only pass through non-blank queries
 *
 * - distinctUntilChanged()
 *   If user types "Batman" then deletes and types "Batman" again,
 *   we don't search twice
 *
 * - debounce(300)
 *   Wait 300ms after user stops typing before searching
 *   If user types "B", "Ba", "Bat", "Batm", "Batma", "Batman"
 *   we only search once for "Batman" (300ms after they stop)
 *
 * - flatMapLatest { }
 *   If user types "Batman" then quickly types "Superman",
 *   cancel the Batman search and start Superman search
 *
 * EXAMPLE CLASS STRUCTURE:
 *
 * @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
 * class PerformSearchReducer(
 *   private val searchRepository: SearchRepository,
 *   private val scope: CoroutineScope,
 *   private val debounceMillis: Long = 300,
 *   private val publishEvent: (SearchEvent) -> Unit
 * ) : Reducer<SearchState, SearchEvent.PerformSearch> {
 *
 *   private val queryChannel = Channel<String>(Channel.CONFLATED)
 *   private var launched = false
 *
 *   override suspend fun reduce(state: SearchState, event: SearchEvent.PerformSearch): SearchState {
 *     // TODO: Implement
 *     //
 *     // 1. If not launched yet, call launchListener() and set launched = true
 *     // 2. Trim the query
 *     // 3. If query is empty, publish ClearSearch event
 *     // 4. Otherwise, send query to channel: queryChannel.trySend(query)
 *     // 5. Return state.copy(query = query)
 *   }
 *
 *   private fun launchListener() {
 *     // TODO: Set up the debounced search pipeline here
 *     // See Step 3 above for the complete pipeline
 *   }
 * }
 *
 * WHY THIS APPROACH?
 * - Channel: Allows us to queue queries and process them in order
 * - Debouncing: Reduces API calls (better performance, lower costs)
 * - flatMapLatest: Cancels old searches when new one starts (no stale results)
 * - Separate events: Clear separation of concerns (searching state, results, errors)
 *
 * TESTING TIP:
 * You can test debouncing by making debounceMillis a constructor parameter
 * and passing a small value (like 10ms) in tests
 *
 * Reference: feature-mvi/src/main/java/com/gyg/codelab/movies/mvi/search/reducer/PerformSearchReducer.kt
 */

// TODO: Implement PerformSearchReducer class here
