package com.dkat.wordbook.ui.compose.bar

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import com.dkat.wordbook.MainViewModel
import com.dkat.wordbook.ui.compose.screen.Screen
import com.dkat.wordbook.ui.compose.screen.screens

@Composable
fun BottomBar(
    navController: NavHostController,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
)
{
    val selectedSource by viewModel.selectedSource.collectAsStateWithLifecycle()

    NavigationBar(
        modifier = modifier
    ) {
        screens.forEach { screen ->
            val label = stringResource(screen.labelResourceId)
            NavigationBarItem(
                icon = {
                    Box() {
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = label
                        )
                    }
                },
                label = {
                    Text(label)
                },
                selected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                    if (screen.route == Screen.Sources.route) {
                        viewModel.selectSource(selectedSource)
                    }
                }
            )
        }
    }
}
