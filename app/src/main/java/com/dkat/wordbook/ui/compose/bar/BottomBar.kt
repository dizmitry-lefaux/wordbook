package com.dkat.wordbook.ui.compose.bar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import com.dkat.wordbook.ui.compose.screen.bottomBarScreens

@Composable
fun BottomBar(
    navController: NavHostController,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    NavigationBar {
        bottomBarScreens.forEach { screen ->
            val label = stringResource(screen.labelResourceId)
            NavigationBarItem(
                icon = {
                    Box {
                        Icon(
                            painterResource(screen.icon!!),
                            contentDescription = stringResource(screen.labelResourceId),
                            modifier = Modifier.size(30.dp)
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
                }
            )
        }
    }
}
