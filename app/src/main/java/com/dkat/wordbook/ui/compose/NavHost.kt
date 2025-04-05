package com.dkat.wordbook.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dkat.wordbook.MainViewModel
import com.dkat.wordbook.ui.compose.screen.Screen
import com.dkat.wordbook.data.Word
import com.dkat.wordbook.ui.compose.screen.home.HomeScreen
import com.dkat.wordbook.ui.compose.screen.session.SessionScreen
import com.dkat.wordbook.ui.compose.screen.source.SourcesScreen

@Composable
fun WordbookNavHost(
    navController: NavHostController,
    viewModel: MainViewModel,
    modifier: Modifier
) {
    val words by viewModel.words.collectAsStateWithLifecycle()
    val sources by viewModel.sources.collectAsStateWithLifecycle()
    val sessionWords by viewModel.sessionWords.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                sources = sources,
                words = words,
                onDeleteWordItemClick = { word: Word ->
                    viewModel.deleteWord(word)
                },
                addWord = { word: Word ->
                    viewModel.addWord(word)
                }
            )
        }
        composable(route = Screen.Sources.route) {
            SourcesScreen(
                sources = sources,
                words = words,
                onDeleteWordItemClick = { word: Word ->
                    viewModel.deleteWord(word)
                }
            )
        }
        composable(route = Screen.Session.route) {
            SessionScreen(
                sessionWords = sessionWords,
                onUpdateSessionClick = {
                    viewModel.updateSession()
                },
                onRestartSessionClick = {
                    viewModel.restartSession()
                }
            )
        }
    }
}
