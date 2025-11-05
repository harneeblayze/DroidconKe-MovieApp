package com.gyg.codelab.movies.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gyg.codelab.movies.domain.model.Movie
import com.gyg.codelab.movies.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AutoSlidingCarousel(
  movies: List<Movie>,
  onMovieClick: (Movie) -> Unit,
  modifier: Modifier = Modifier,
  slideDuration: Long = 3000L
) {
  if (movies.isEmpty()) return

  val isDarkTheme = isSystemInDarkTheme()
  val primaryTextColor = if (isDarkTheme) TextPrimary else Color.White
  val secondaryTextColor = if (isDarkTheme) TextSecondary else Color.White.copy(alpha = 0.8f)
  val indicatorActiveColor = if (isDarkTheme) TextPrimary else TextPrimaryLight
  val indicatorInactiveColor = if (isDarkTheme) TextTertiary else TextTertiaryLight

  var currentIndex by remember { mutableStateOf(0) }
  val scope = rememberCoroutineScope()

  // Auto-slide effect
  LaunchedEffect(movies) {
    while (true) {
      delay(slideDuration)
      currentIndex = (currentIndex + 1) % movies.size
    }
  }

  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    // Carousel Card
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .height(220.dp)
        .padding(horizontal = 16.dp)
        .clickable { onMovieClick(movies[currentIndex]) },
      shape = RoundedCornerShape(16.dp),
      elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
      Box(modifier = Modifier.fillMaxSize()) {
        // Movie Poster
        movies[currentIndex].posterPath?.let { posterPath ->
          AsyncImage(
            model = "https://image.tmdb.org/t/p/w500$posterPath",
            contentDescription = movies[currentIndex].title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
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
                  Color.Black.copy(alpha = 0.7f)
                ),
                startY = 300f
              )
            )
        )

        // Movie Info
        Column(
          modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(16.dp)
        ) {
          Text(
            text = movies[currentIndex].title,
            style = MaterialTheme.typography.titleLarge.copy(
              color = primaryTextColor,
              fontWeight = FontWeight.Bold,
              fontSize = 20.sp
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
          )

          Spacer(modifier = Modifier.height(4.dp))

          Row(
            verticalAlignment = Alignment.CenterVertically
          ) {
            RatingBadge(
              rating = movies[currentIndex].voteAverage,
              showIcon = true
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
              text = movies[currentIndex].releaseDate.take(4),
              style = MaterialTheme.typography.bodyMedium,
              color = secondaryTextColor
            )
          }
        }
      }
    }

    // Page Indicators
    Spacer(modifier = Modifier.height(12.dp))

    Row(
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically
    ) {
      val displayCount = minOf(5, movies.size)
      repeat(displayCount) { index ->
        val isSelected = currentIndex % displayCount == index
        val size by animateDpAsState(
          targetValue = if (isSelected) 10.dp else 6.dp,
          label = "indicator_size"
        )

        Box(
          modifier = Modifier
            .padding(horizontal = 4.dp)
            .size(size)
            .clip(CircleShape)
            .background(
              if (isSelected) indicatorActiveColor else indicatorInactiveColor
            )
            .clickable {
              scope.launch {
                currentIndex = index
              }
            }
        )
      }
    }
  }
}