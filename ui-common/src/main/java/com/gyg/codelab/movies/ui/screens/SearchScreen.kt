package com.gyg.codelab.movies.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.ui.components.SearchBar
import com.gyg.codelab.movies.ui.components.SearchResultCard
import com.gyg.codelab.movies.ui.theme.*

@Composable
fun SearchScreen(
  query: String,
  searchResults: List<Movie>,
  modifier: Modifier = Modifier,
  onQueryChange: (String) -> Unit,
  onMovieClick: (Movie) -> Unit,
  onToggleFavorite: (Movie) -> Unit = {},

) {
  val isDarkTheme = isSystemInDarkTheme()
  val secondaryTextColor = if (isDarkTheme) TextSecondary else TextSecondaryLight

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .padding(top = 46.dp)
  ) {
    // Search Bar
    SearchBar(
      query = query,
      onQueryChange = onQueryChange,
      onSearch = { /* Search is triggered on query change */ },
      modifier = Modifier.padding(horizontal = 16.dp)
    )

    // Search Results
    if (query.isEmpty()) {
      // Empty state
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(32.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = "🎬",
            style = MaterialTheme.typography.displayLarge
          )
          Spacer(modifier = Modifier.height(16.dp))
          Text(
            text = "Search for your favorite movies",
            style = MaterialTheme.typography.bodyLarge,
            color = secondaryTextColor,
            textAlign = TextAlign.Center
          )
        }
      }
    } else if (searchResults.isEmpty()) {
      // No results state
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(32.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = "😔",
            style = MaterialTheme.typography.displayLarge
          )
          Spacer(modifier = Modifier.height(16.dp))
          Text(
            text = "No movies found",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Try searching with different keywords",
            style = MaterialTheme.typography.bodyMedium,
            color = secondaryTextColor,
            textAlign = TextAlign.Center
          )
        }
      }
    } else {
      // Results list
      LazyColumn(
        contentPadding = PaddingValues(
          horizontal = 16.dp,
          vertical = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(bottom = 80.dp)
      ) {
        item {
          Text(
            text = "${searchResults.size} results found",
            style = MaterialTheme.typography.bodyMedium,
            color = secondaryTextColor,
            modifier = Modifier.padding(bottom = 8.dp)
          )
        }

        items(searchResults) { movie ->
          SearchResultCard(
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