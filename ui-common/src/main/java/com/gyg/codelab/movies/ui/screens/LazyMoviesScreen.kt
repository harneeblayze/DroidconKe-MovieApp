package com.gyg.codelab.movies.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.domain.model.MovieCategory
import com.gyg.codelab.movies.ui.components.*
import com.gyg.codelab.movies.ui.theme.*
import kotlinx.coroutines.flow.Flow

/**
 * Pure UI component for displaying movie sections
 *
 * This is a reusable, architecture-agnostic component that displays movie categories.
 * It has NO navigation logic - that's handled by the app layer.
 * Automatically adapts to light/dark theme.
 *
 * ## Responsibilities:
 * - Display movie sections with lazy loading
 * - Show loading states per category
 * - Display error states with retry option
 * - Render different card types for different categories
 * - Adapt to system theme (light/dark)
 *
 * ## NOT Responsible For:
 * - Navigation (handled by app layer)
 * - Business logic (handled by ViewModels)
 * - Tab switching (handled by app navigation)
 *
 * @param movieSections List of movie sections to display
 * @param onMovieClick Callback when a movie is clicked
 * @param onSearchClick Callback when search bar is clicked
 * @param onRetry Callback when retry is clicked for a failed category
 * @param onToggleFavorite Callback when favorite is toggled
 * @param modifier Optional modifier
 */
@Composable
fun LazyMoviesScreen(
  movieSections: List<MovieSection>,
  modifier: Modifier = Modifier,
  onMovieClick: (Movie) -> Unit,
  onSearchClick: () -> Unit = {},
  onRetry: (MovieCategory) -> Unit = {},
  onToggleFavorite: (Movie) -> Unit = {},
  listState: LazyListState = rememberLazyListState(),
) {
  LazyColumn(
    state = listState,
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background),
    contentPadding = PaddingValues(bottom = 80.dp)
  ) {
    // Header with search bar
    item(key = "header") {
      MoviesHeader(onSearchClick = onSearchClick)
    }

    // Movie sections
    movieSections.forEach { section ->
      when (section) {
        is MovieSection.Carousel -> {
          item(key = section.category) {
            val movies by section.movies.collectAsStateWithLifecycle(initialValue = emptyList())

            when {
              movies.isNotEmpty() -> {
                Spacer(modifier = Modifier.height(20.dp))
                AutoSlidingCarousel(
                  movies = movies.take(5),
                  onMovieClick = onMovieClick
                )
              }

              section.error != null -> {
                Spacer(modifier = Modifier.height(20.dp))
                ErrorState(
                  error = section.error,
                  onRetry = { onRetry(section.category) },
                  modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(horizontal = 16.dp)
                )
              }

              else -> {
                Spacer(modifier = Modifier.height(20.dp))
                CarouselShimmer()
              }
            }
          }
        }

        is MovieSection.Regular -> {
          item(key = section.category) {
            val movies by section.movies.collectAsStateWithLifecycle(initialValue = emptyList())

            RegularMovieSection(
              title = section.title,
              movies = movies,
              isLoading = section.isLoading,
              error = section.error,
              onMovieClick = onMovieClick,
              category = section.category,
              onRetry = onRetry,
              onToggleFavorite = onToggleFavorite
            )
          }
        }
      }
    }
  }
}

/**
 * Header component with search bar
 */
@Composable
private fun MoviesHeader(
  onSearchClick: () -> Unit
) {
  val textColor = MaterialTheme.colorScheme.onBackground

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 46.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
  ) {
    Text(
      text = "What would you like to watch?",
      style = MaterialTheme.typography.headlineSmall.copy(
        color = textColor,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
      )
    )

    Spacer(modifier = Modifier.height(16.dp))

    ClickableSearchBar(onClick = onSearchClick)
  }
}

/**
 * Regular movie section with title and horizontal list
 */
@Composable
private fun RegularMovieSection(
  title: String,
  movies: List<Movie>,
  isLoading: Boolean,
  error: String?,
  onMovieClick: (Movie) -> Unit,
  category: MovieCategory,
  onRetry: (MovieCategory) -> Unit,
  onToggleFavorite: (Movie) -> Unit
) {
  val isDarkTheme = isSystemInDarkTheme()
  val titleColor = if (isDarkTheme) GoldStar else GoldStarDark

  Column(
    modifier = Modifier.padding(top = 24.dp)
  ) {
    // Section Title
    Text(
      text = title,
      style = MaterialTheme.typography.titleLarge.copy(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = titleColor
      ),
      modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )

    Spacer(modifier = Modifier.height(12.dp))

    when {
      isLoading -> {
        // Loading state - show shimmer cards
        LazyRow(
          contentPadding = PaddingValues(horizontal = 12.dp),
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          items(5) {
            ShimmerCard()
          }
        }
      }

      error != null -> {
        // Error state
        ErrorState(
          error = error,
          onRetry = { onRetry(category) },
          modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(16.dp)
        )
      }

      movies.isNotEmpty() -> {
        // Movies list - different card types based on category
        LazyRow(
          contentPadding = PaddingValues(horizontal = 12.dp),
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          items(movies.take(10)) { movie ->
            when (category) {
              MovieCategory.UPCOMING -> {
                DiscoverMovieCard(
                  movie = movie,
                  onClick = onMovieClick,
                  isFavorite = movie.isFavorite,
                  onFavoriteClick = onToggleFavorite
                )
              }

              MovieCategory.NOW_PLAYING -> {
                FeaturedMovieCard(
                  movie = movie,
                  onClick = onMovieClick,
                  isFavorite = movie.isFavorite,
                  onFavoriteClick = onToggleFavorite
                )
              }

              else -> {
                CompactMovieCard(
                  movie = movie,
                  onClick = onMovieClick,
                  isFavorite = movie.isFavorite,
                  onFavoriteClick = onToggleFavorite
                )
              }
            }
          }
        }
      }

      else -> {
        // Empty state
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "Loading...",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
    }
  }
}

/**
 * Shimmer loading card placeholder
 */
@Composable
private fun ShimmerCard() {
  val isDarkTheme = isSystemInDarkTheme()
  val cardColor = if (isDarkTheme) DarkCardBackground else LightCardBackground
  val shimmerColors = if (isDarkTheme) {
    listOf(
      Color.Gray.copy(alpha = 0.3f),
      Color.Gray.copy(alpha = 0.1f),
      Color.Gray.copy(alpha = 0.3f)
    )
  } else {
    listOf(
      Color.LightGray.copy(alpha = 0.4f),
      Color.LightGray.copy(alpha = 0.2f),
      Color.LightGray.copy(alpha = 0.4f)
    )
  }

  Card(
    modifier = Modifier
      .width(130.dp)
      .height(220.dp),
    shape = RoundedCornerShape(8.dp),
    colors = CardDefaults.cardColors(
      containerColor = cardColor
    )
  ) {
    Column {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(180.dp)
          .background(
            Brush.horizontalGradient(colors = shimmerColors)
          )
      )

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(16.dp)
          .padding(horizontal = 8.dp, vertical = 4.dp)
          .background(
            if (isDarkTheme) Color.Gray.copy(alpha = 0.2f) else Color.LightGray.copy(alpha = 0.3f),
            shape = RoundedCornerShape(4.dp)
          )
      )
    }
  }
}

/**
 * Carousel shimmer loading placeholder
 */
@Composable
private fun CarouselShimmer() {
  val isDarkTheme = isSystemInDarkTheme()
  val cardColor = if (isDarkTheme) DarkCardBackground else LightCardBackground
  val shimmerColors = if (isDarkTheme) {
    listOf(
      Color.Gray.copy(alpha = 0.3f),
      Color.Gray.copy(alpha = 0.1f),
      Color.Gray.copy(alpha = 0.3f)
    )
  } else {
    listOf(
      Color.LightGray.copy(alpha = 0.4f),
      Color.LightGray.copy(alpha = 0.2f),
      Color.LightGray.copy(alpha = 0.4f)
    )
  }

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .height(200.dp)
      .padding(horizontal = 16.dp),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = cardColor
    )
  ) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(
          Brush.horizontalGradient(colors = shimmerColors)
        )
    )
  }
}

/**
 * Error state component with retry button
 */
@Composable
private fun ErrorState(
  error: String,
  onRetry: () -> Unit,
  modifier: Modifier = Modifier
) {
  val isDarkTheme = isSystemInDarkTheme()
  val buttonColor = if (isDarkTheme) GoldStar else GoldStarDark
  val buttonTextColor = if (isDarkTheme) DarkBackground else Color.White

  Box(
    modifier = modifier,
    contentAlignment = Alignment.Center
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      Text(
        text = "😔",
        style = MaterialTheme.typography.displayMedium
      )
      Text(
        text = error,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center
      )
      Button(
        onClick = onRetry,
        colors = ButtonDefaults.buttonColors(
          containerColor = buttonColor,
          contentColor = buttonTextColor
        )
      ) {
        Text("Retry")
      }
    }
  }
}

/**
 * Discover movie card - wide card with gradient overlay
 * Used for Upcoming Movies section
 */
@Composable
private fun DiscoverMovieCard(
  movie: Movie,
  onClick: (Movie) -> Unit,
  isFavorite: Boolean = false,
  onFavoriteClick: (Movie) -> Unit = {}
) {
  val isDarkTheme = isSystemInDarkTheme()
  val textColor = if (isDarkTheme) TextPrimary else Color.White

  Card(
    modifier = Modifier
      .width(220.dp)
      .height(130.dp)
      .clickable { onClick(movie) },
    shape = RoundedCornerShape(12.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
  ) {
    Box(modifier = Modifier.fillMaxSize()) {
      // Movie Poster
      movie.posterPath?.let { posterPath ->
        AsyncImage(
          model = "https://image.tmdb.org/t/p/w300$posterPath",
          contentDescription = movie.title,
          modifier = Modifier.fillMaxSize(),
          contentScale = ContentScale.Crop
        )
      }

      // Gradient Overlay (dark for both themes to ensure text visibility)
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(
            Brush.verticalGradient(
              listOf(
                Color.Transparent,
                Color.Black.copy(alpha = 0.8f)
              )
            )
          )
      )

      // Favorite button
      IconButton(
        onClick = { onFavoriteClick(movie) },
        modifier = Modifier
          .align(Alignment.TopEnd)
          .padding(4.dp)
      ) {
        Icon(
          imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
          contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
          tint = if (isFavorite) Color.Red else Color.White,
          modifier = Modifier.size(24.dp)
        )
      }

      // Title
      Text(
        text = movie.title,
        style = MaterialTheme.typography.bodyLarge.copy(
          fontWeight = FontWeight.Medium,
          color = Color.White // Always white on dark gradient
        ),
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .padding(12.dp)
          .fillMaxWidth(),
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Ellipsis,
        maxLines = 2
      )
    }
  }
}

/**
 * Featured movie card - tall card for prominent display
 * Used for Now Playing section
 */
@Composable
private fun FeaturedMovieCard(
  movie: Movie,
  onClick: (Movie) -> Unit,
  isFavorite: Boolean = false,
  onFavoriteClick: (Movie) -> Unit = {}
) {
  val isDarkTheme = isSystemInDarkTheme()
  val cardColor = if (isDarkTheme) DarkCardBackground else LightCardBackground
  val textColor = MaterialTheme.colorScheme.onSurface

  Card(
    modifier = Modifier
      .width(160.dp)
      .height(240.dp)
      .clickable { onClick(movie) },
    shape = RoundedCornerShape(12.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    colors = CardDefaults.cardColors(containerColor = cardColor)
  ) {
    Column {
      Box {
        movie.posterPath?.let { posterPath ->
          AsyncImage(
            model = "https://image.tmdb.org/t/p/w300$posterPath",
            contentDescription = movie.title,
            modifier = Modifier
              .fillMaxWidth()
              .height(200.dp),
            contentScale = ContentScale.Crop
          )
        }

        // Favorite button
        IconButton(
          onClick = { onFavoriteClick(movie) },
          modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(4.dp)
        ) {
          Icon(
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
            tint = if (isFavorite) Color.Red else Color.White,
            modifier = Modifier.size(20.dp)
          )
        }
      }

      Text(
        text = movie.title,
        style = MaterialTheme.typography.bodyMedium.copy(
          fontWeight = FontWeight.Medium,
          color = textColor
        ),
        modifier = Modifier.padding(8.dp),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )
    }
  }
}

/**
 * Compact movie card - standard card
 * Used for Popular and Top Rated sections
 */
@Composable
private fun CompactMovieCard(
  movie: Movie,
  onClick: (Movie) -> Unit,
  isFavorite: Boolean = false,
  onFavoriteClick: (Movie) -> Unit = {}
) {
  val isDarkTheme = isSystemInDarkTheme()
  val cardColor = if (isDarkTheme) DarkCardBackground else LightCardBackground
  val textColor = MaterialTheme.colorScheme.onSurface

  Card(
    modifier = Modifier
      .width(130.dp)
      .height(220.dp)
      .clickable { onClick(movie) },
    shape = RoundedCornerShape(8.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    colors = CardDefaults.cardColors(containerColor = cardColor)
  ) {
    Column {
      Box {
        movie.posterPath?.let { posterPath ->
          AsyncImage(
            model = "https://image.tmdb.org/t/p/w200$posterPath",
            contentDescription = movie.title,
            modifier = Modifier
              .fillMaxWidth()
              .height(180.dp),
            contentScale = ContentScale.Crop
          )
        }

        // Favorite button
        IconButton(
          onClick = { onFavoriteClick(movie) },
          modifier = Modifier
            .align(Alignment.TopEnd)
            .size(28.dp)
            .padding(2.dp)
        ) {
          Icon(
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
            tint = if (isFavorite) Color.Red else Color.White.copy(alpha = 0.9f),
            modifier = Modifier.size(18.dp)
          )
        }
      }

      Text(
        text = movie.title,
        style = MaterialTheme.typography.bodySmall.copy(
          color = textColor
        ),
        modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )
    }
  }
}

/**
 * Data class representing a movie section
 * This is architecture-agnostic and can be created by any architecture
 */
sealed class MovieSection {
  abstract val category: MovieCategory
  abstract val movies: Flow<List<Movie>>
  abstract val error: String?

  data class Carousel(
    override val category: MovieCategory,
    override val movies: Flow<List<Movie>>,
    override val error: String? = null
  ) : MovieSection()

  data class Regular(
    override val category: MovieCategory,
    override val movies: Flow<List<Movie>>,
    val title: String,
    val isLoading: Boolean = false,
    override val error: String? = null
  ) : MovieSection()
}