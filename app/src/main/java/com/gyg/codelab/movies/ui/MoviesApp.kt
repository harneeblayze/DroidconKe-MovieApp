package com.gyg.codelab.movies.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.gyg.codelab.movies.navigation.MoviesNavHost
import com.gyg.codelab.movies.navigation.TopLevelDestination
import com.gyg.codelab.movies.ui.theme.*

/**
 * Main app composable following Now in Android pattern
 */
@Composable
fun MoviesApp(
  appState: MoviesAppState = rememberMoviesAppState(),
) {
  Scaffold(
    bottomBar = {
      MoviesBottomBar(
        destinations = appState.topLevelDestinations,
        currentDestination = appState.currentTopLevelDestination,
        onNavigateToDestination = appState::navigateToTopLevelDestination,
      )
    },
  ) { padding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(padding)
    ) {
      MoviesNavHost(
        appState = appState,
        onMovieClick = { movie ->
          // TODO: Navigate to movie details
          // This would typically navigate to a detail screen
        },
      )
    }
  }
}

/**
 * Bottom navigation bar with proper theming and styling
 * Automatically adapts to light/dark theme
 */
@Composable
private fun MoviesBottomBar(
  destinations: List<TopLevelDestination>,
  currentDestination: TopLevelDestination?,
  onNavigateToDestination: (TopLevelDestination) -> Unit,
  modifier: Modifier = Modifier,
) {
  val isDarkTheme = isSystemInDarkTheme()

  NavigationBar(
    containerColor = if (isDarkTheme) DarkSurface else LightSurface,
    contentColor = if (isDarkTheme) TextTertiary else TextTertiaryLight,
    modifier = modifier
  ) {
    destinations.forEach { destination ->
      val selected = destination == currentDestination

      NavigationBarItem(
        icon = {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
              imageVector = if (selected) {
                destination.selectedIcon
              } else {
                destination.unselectedIcon
              },
              contentDescription = destination.iconTextId,
            )
          }
        },
        label = {
          Text(
            text = destination.iconTextId,
            style = MaterialTheme.typography.labelSmall
          )
        },
        alwaysShowLabel = true,
        selected = selected,
        onClick = { onNavigateToDestination(destination) },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = if (isDarkTheme) TextPrimary else TextPrimaryLight,
          selectedTextColor = if (isDarkTheme) TextPrimary else TextPrimaryLight,
          unselectedIconColor = if (isDarkTheme) TextTertiary else TextTertiaryLight,
          unselectedTextColor = if (isDarkTheme) TextTertiary else TextTertiaryLight,
          indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        ),
      )
    }
  }
}
