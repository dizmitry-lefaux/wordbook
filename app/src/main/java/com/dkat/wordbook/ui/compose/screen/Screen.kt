package com.dkat.wordbook.ui.compose.screen

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.dkat.wordbook.R

sealed class Screen(
    val route: String,
    val icon: ImageVector,
    @StringRes
    val labelResourceId: Int
)
{
    data object Home: Screen(
        route = "home",
        icon = Icons.Filled.Home,
        labelResourceId = R.string.home_label
    )
    data object Sources: Screen(
        route = "sources",
        icon = Icons.Filled.Edit,
        labelResourceId = R.string.sources_label
    )
    data object Session: Screen(
        route = "session",
        icon = Icons.Filled.Face,
        labelResourceId = R.string.session_label
    )
}

val screens = listOf(Screen.Home, Screen.Sources, Screen.Session)
