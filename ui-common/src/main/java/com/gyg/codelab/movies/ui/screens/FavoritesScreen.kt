package com.gyg.codelab.movies.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.ui.components.DetailedMovieCard
import com.gyg.codelab.movies.ui.theme.*

@Composable
fun FavoritesScreen(
    favoriteMovies: List<Movie>,
    onMovieClick: (Movie) -> Unit,
    onToggleFavorite: (Movie) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isDarkTheme = isSystemInDarkTheme()
    val secondaryTextColor = if (isDarkTheme) TextSecondary else TextSecondaryLight
    val tertiaryTextColor = if (isDarkTheme) TextTertiary else TextTertiaryLight

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 46.dp),
    ) {
        // Header
        Text(
            text = "My Favorites",
            style = MaterialTheme.typography.headlineSmall.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            ),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
        )

        if (favoriteMovies.isEmpty()) {
            // Empty state
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = tertiaryTextColor,
                        modifier = Modifier.size(80.dp),
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "No favorites yet",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Movies you mark as favorite will appear here",
                        style = MaterialTheme.typography.bodyMedium,
                        color = secondaryTextColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(0.8f),
                    )
                }
            }
        } else {
            // Favorites list
            LazyColumn(
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 8.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(bottom = 80.dp),
            ) {
                item {
                    Text(
                        text = "${favoriteMovies.size} movie${if (favoriteMovies.size != 1) "s" else ""} in your collection",
                        style = MaterialTheme.typography.bodyMedium,
                        color = secondaryTextColor,
                        modifier = Modifier.padding(bottom = 8.dp),
                    )
                }

                items(
                    items = favoriteMovies,
                    key = { movie -> movie.id },
                ) { movie ->
                    DetailedMovieCard(
                        movie = movie,
                        onClick = onMovieClick,
                    )
                }
            }
        }
    }
}
