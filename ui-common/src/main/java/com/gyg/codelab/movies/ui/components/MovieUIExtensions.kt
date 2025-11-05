package com.gyg.codelab.movies.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gyg.codelab.movies.ui.theme.*

/**
 * Rating badge component
 */
@Composable
fun RatingBadge(
  rating: Double,
  modifier: Modifier = Modifier,
  showIcon: Boolean = true
) {
  Surface(
    modifier = modifier,
    shape = RoundedCornerShape(16.dp),
    color = DarkCardBackground
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      if (showIcon) {
        Icon(
          imageVector = Icons.Filled.Star,
          contentDescription = "Rating",
          tint = GoldStar,
          modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
      }
      Text(
        text = String.format("%.1f", rating),
        style = MaterialTheme.typography.labelMedium.copy(
          fontWeight = FontWeight.Medium,
          color = TextPrimary
        )
      )
    }
  }
}

/**
 * Favorite button with animation support
 */
@Composable
fun FavoriteButton(
  isFavorite: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  IconButton(
    onClick = onClick,
    modifier = modifier
      .clip(CircleShape)
      .background(
        color = DarkCardBackground.copy(alpha = 0.8f)
      )
  ) {
    Icon(
      imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
      contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
      tint = if (isFavorite) Color.Red else TextPrimary
    )
  }
}

/**
 * Play button overlay for movie cards
 */
@Composable
fun PlayButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  FloatingActionButton(
    onClick = onClick,
    modifier = modifier,
    containerColor = MaterialTheme.colorScheme.primary,
    contentColor = Color.White,
    shape = CircleShape
  ) {
    Icon(
      imageVector = Icons.Filled.PlayArrow,
      contentDescription = "Play trailer",
      modifier = Modifier.size(24.dp)
    )
  }
}

/**
 * Genre chip component
 */
@Composable
fun GenreChip(
  genre: String,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier,
    shape = RoundedCornerShape(16.dp),
    color = DarkSurfaceVariant
  ) {
    Text(
      text = genre,
      modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
      style = MaterialTheme.typography.labelSmall,
      color = TextSecondary
    )
  }
}

/**
 * Movie info row component
 */
@Composable
fun MovieInfoRow(
  label: String,
  value: String,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = label,
      style = MaterialTheme.typography.bodyMedium,
      color = TextTertiary
    )
    Text(
      text = value,
      style = MaterialTheme.typography.bodyMedium.copy(
        fontWeight = FontWeight.Medium
      ),
      color = TextPrimary
    )
  }
}

/**
 * Section header component
 */
@Composable
fun SectionHeader(
  title: String,
  modifier: Modifier = Modifier,
  onSeeAllClick: (() -> Unit)? = null,

) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = title,
      style = MaterialTheme.typography.titleLarge.copy(
        fontWeight = FontWeight.Bold,
        color = TextPrimary
      )
    )
    onSeeAllClick?.let {
      TextButton(onClick = it) {
        Text(
          text = "See all",
          color = MaterialTheme.colorScheme.primary
        )
      }
    }
  }
}

/**
 * Loading card placeholder
 */
@Composable
fun MovieCardPlaceholder(
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .height(150.dp),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(
      containerColor = DarkCardBackground
    )
  ) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(
          shimmerBrush(
            targetValue = 1000f,
            showShimmer = true
          )
        )
    )
  }
}

/**
 * Shimmer effect for loading states
 */
@Composable
private fun shimmerBrush(
  targetValue: Float = 1000f,
  showShimmer: Boolean = true
): androidx.compose.ui.graphics.Brush {
  return if (showShimmer) {
    val shimmerColors = listOf(
      DarkSurfaceVariant,
      DarkCardBackground,
      DarkSurfaceVariant,
    )
    androidx.compose.ui.graphics.Brush.linearGradient(
      colors = shimmerColors,
      start = androidx.compose.ui.geometry.Offset(0f, 0f),
      end = androidx.compose.ui.geometry.Offset(targetValue, targetValue)
    )
  } else {
    androidx.compose.ui.graphics.Brush.linearGradient(
      colors = listOf(DarkSurfaceVariant, DarkSurfaceVariant)
    )
  }
}