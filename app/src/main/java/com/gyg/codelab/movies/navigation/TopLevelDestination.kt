package com.gyg.codelab.movies.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Type for the top level destinations in the application.
 * Contains metadata about the destination that is used in the bottom navigation bar.
 *
 * @param selectedIcon The icon to be displayed in the navigation UI when this destination is selected.
 * @param unselectedIcon The icon to be displayed in the navigation UI when this destination is not selected.
 * @param iconTextId Text that to be displayed in the navigation UI.
 * @param titleText Text that is displayed on the top app bar.
 */
enum class TopLevelDestination(
  val selectedIcon: ImageVector,
  val unselectedIcon: ImageVector,
  val iconTextId: String,
  val titleText: String,
) {
  HOME(
    selectedIcon = Icons.Filled.Home,
    unselectedIcon = Icons.Outlined.Home,
    iconTextId = "Home",
    titleText = "Movies",
  ),
  SEARCH(
    selectedIcon = Icons.Filled.Search,
    unselectedIcon = Icons.Outlined.Search,
    iconTextId = "Search",
    titleText = "Search",
  ),
  FAVORITES(
    selectedIcon = Icons.Filled.Favorite,
    unselectedIcon = Icons.Outlined.FavoriteBorder,
    iconTextId = "Favorites",
    titleText = "Favorites",
  ),
}
