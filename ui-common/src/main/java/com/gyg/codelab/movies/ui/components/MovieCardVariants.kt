package com.gyg.codelab.movies.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.ui.theme.*

/**
 * Compact vertical movie card with modern dark design
 * Perfect for grid layouts
 */
@Composable
fun CompactMovieCard(
    movie: Movie,
    onClick: (Movie) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isDarkTheme = isSystemInDarkTheme()
    val cardColor = if (isDarkTheme) DarkCardBackground else LightCardBackground
    val textColor = if (isDarkTheme) TextPrimary else TextPrimaryLight
    val secondaryTextColor = if (isDarkTheme) TextSecondary else TextSecondaryLight
    val accentColor = if (isDarkTheme) GoldStar else GoldStarDark

    Column(
        modifier = modifier
            .width(130.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(cardColor)
            .clickable { onClick(movie) },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Movie Poster
        movie.posterPath?.let { posterPath ->
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w185$posterPath",
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop,
            )
        }

        // Content
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            // Title
            Text(
                text = movie.title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = textColor,
                    fontWeight = FontWeight.Medium,
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Rating
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Rating",
                    tint = accentColor,
                    modifier = Modifier.size(14.dp),
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = String.format("%.1f", movie.voteAverage),
                    style = MaterialTheme.typography.bodySmall,
                    color = secondaryTextColor,
                )
            }
        }
    }
}

/**
 * Horizontal movie card with rich details
 * Perfect for list layouts
 */
@Composable
fun DetailedMovieCard(
    movie: Movie,
    onClick: (Movie) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isDarkTheme = isSystemInDarkTheme()
    val cardColor = if (isDarkTheme) DarkCardBackground else LightCardBackground
    val textColor = if (isDarkTheme) TextPrimary else TextPrimaryLight
    val secondaryTextColor = if (isDarkTheme) TextSecondary else TextSecondaryLight
    val tertiaryTextColor = if (isDarkTheme) TextTertiary else TextTertiaryLight
    val accentColor = if (isDarkTheme) GoldStar else GoldStarDark

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick(movie) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor,
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
        ) {
            // Poster
            movie.posterPath?.let { posterPath ->
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w185$posterPath",
                    contentDescription = movie.title,
                    modifier = Modifier
                        .width(80.dp)
                        .fillMaxHeight(),
                    contentScale = ContentScale.Crop,
                )
            }

            // Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                // Title
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = textColor,
                        fontWeight = FontWeight.Bold,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                // Overview
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodySmall,
                    color = tertiaryTextColor,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                // Bottom Info Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Rating
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Rating",
                            tint = accentColor,
                            modifier = Modifier.size(14.dp),
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = String.format("%.1f", movie.voteAverage),
                            style = MaterialTheme.typography.labelSmall,
                            color = secondaryTextColor,
                        )
                    }

                    // Release Date
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Release Date",
                            tint = tertiaryTextColor,
                            modifier = Modifier.size(14.dp),
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = movie.releaseDate.take(4), // Year only
                            style = MaterialTheme.typography.labelSmall,
                            color = tertiaryTextColor,
                        )
                    }
                }
            }
        }
    }
}

/**
 * Featured movie card with overlay gradient
 * Perfect for hero sections and featured content
 */
@Composable
fun FeaturedMovieCard(
    movie: Movie,
    onClick: (Movie) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isDarkTheme = isSystemInDarkTheme()
    val accentColor = if (isDarkTheme) GoldStar else GoldStarDark
    val secondaryTextColor = if (isDarkTheme) TextSecondary else Color.White.copy(alpha = 0.8f)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = { onClick(movie) },
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            // Background Poster
            movie.posterPath?.let { posterPath ->
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500$posterPath",
                    contentDescription = movie.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            }

            // Gradient Overlay (always dark to ensure text visibility)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.8f),
                            ),
                            startY = 100f,
                        ),
                    ),
            )

            // Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
            ) {
                // Title
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Color.White, // Always white on dark gradient
                        fontWeight = FontWeight.Bold,
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Info Row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    // Rating
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Rating",
                            tint = accentColor,
                            modifier = Modifier.size(18.dp),
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = String.format("%.1f", movie.voteAverage),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White, // Always white on dark gradient
                            fontWeight = FontWeight.Medium,
                        )
                    }

                    // Release Year
                    Text(
                        text = movie.releaseDate.take(4),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.9f), // Always white on dark gradient
                    )
                }
            }
        }
    }
}

/**
 * Minimal movie card for search results with favorite button
 * Clean and focused design
 */
@Composable
fun SearchResultCard(
    movie: Movie,
    onClick: (Movie) -> Unit,
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false,
    onFavoriteClick: ((Movie) -> Unit)? = null,
) {
    val isDarkTheme = isSystemInDarkTheme()
    val surfaceColor = if (isDarkTheme) DarkSurfaceVariant else LightSurfaceVariant
    val textColor = if (isDarkTheme) TextPrimary else TextPrimaryLight
    val secondaryTextColor = if (isDarkTheme) TextSecondary else TextSecondaryLight
    val tertiaryTextColor = if (isDarkTheme) TextTertiary else TextTertiaryLight
    val accentColor = if (isDarkTheme) GoldStar else GoldStarDark

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(surfaceColor)
            .clickable { onClick(movie) }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Poster Thumbnail
        movie.posterPath?.let { posterPath ->
            Card(
                modifier = Modifier.size(60.dp),
                shape = RoundedCornerShape(6.dp),
            ) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w92$posterPath",
                    contentDescription = movie.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Content
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = textColor,
                    fontWeight = FontWeight.Medium,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.height(2.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Rating",
                    tint = accentColor,
                    modifier = Modifier.size(12.dp),
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = String.format("%.1f", movie.voteAverage),
                    style = MaterialTheme.typography.bodySmall,
                    color = secondaryTextColor,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "• ${movie.releaseDate.take(4)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = tertiaryTextColor,
                )
            }
        }

        // Favorite button
        if (onFavoriteClick != null) {
            IconButton(
                onClick = { onFavoriteClick(movie) },
                modifier = Modifier.size(36.dp),
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                    tint = if (isFavorite) Color.Red else secondaryTextColor,
                    modifier = Modifier.size(20.dp),
                )
            }
        }
    }
}
