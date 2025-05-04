package com.dkat.wordbook.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dkat.wordbook.MainViewModel
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.Word_B
import com.dkat.wordbook.ui.compose.screen.Screen
import com.dkat.wordbook.ui.compose.screen.books.BooksScreen
import com.dkat.wordbook.ui.compose.screen.home.HomeScreen
import com.dkat.wordbook.ui.compose.screen.session.SessionScreen
import com.dkat.wordbook.ui.compose.screen.words.WordsScreen

@Composable
fun WordbookNavHost(
    navController: NavHostController,
    viewModel: MainViewModel,
    modifier: Modifier
) {
    val sources by viewModel.sources.collectAsStateWithLifecycle()
    val sourcesWithWords by viewModel.sourcesWithWords.collectAsStateWithLifecycle()
    val wordsWithTranslations by viewModel.wordsWithTranslations.collectAsStateWithLifecycle()
    val languages by viewModel.languages.collectAsStateWithLifecycle()
    val sessionWords by viewModel.sessionWords.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                onDeleteWordItemClick = { word: Word_B ->
                    viewModel.deleteWord(word)
                },
                onDeleteSourceItemClick = { source: Source ->
                    viewModel.deleteSource(source)
                },
                sourcesWithWords = sourcesWithWords,
                wordsWithTranslations = wordsWithTranslations,
                readSource = { id: Int ->
                    viewModel.readSource(id)
                },
                updateWordWithTranslations = { word: Word_B, translations: List<Translation> ->
                    viewModel.updateWordWithTranslations(word, translations)
                },
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
        composable(route = Screen.Words.route) {
            WordsScreen(
                readSource = { id: Int ->
                    viewModel.readSource(id)
                },
                createWordWithTranslations = { word: Word_B, translations: List<Translation> ->
                    viewModel.createWordWithTranslations(word, translations)
                },
                sources = sources,
                onDeleteWordItemClick = { word: Word_B ->
                    viewModel.deleteWord(word)
                },
                wordsWithTranslations = wordsWithTranslations,
                updateWordWithTranslations = { word: Word_B, translations: List<Translation> ->
                    viewModel.updateWordWithTranslations(word, translations)
                },
            )
        }
        composable(route = Screen.Books.route) {
            BooksScreen(
                sourcesWithWords = sourcesWithWords,
                wordsWithTranslations = wordsWithTranslations,
                languages = languages,
                onDeleteSourceItemClick = { source: Source ->
                    viewModel.deleteSource(source)
                },
                onDeleteWordItemClick = { word: Word_B ->
                    viewModel.deleteWord(word)
                },
                createSource = { source: Source ->
                    viewModel.createSource(source)
                },
                createLanguage = { language: Language ->
                    viewModel.createLanguage(language)
                },
                onDeleteLanguageItemClick = { language: Language ->
                    viewModel.deleteLanguage(language)
                },
                readSource = { id: Int ->
                    viewModel.readSource(id)
                },
                updateWordWithTranslations = { word: Word_B, translations: List<Translation> ->
                    viewModel.updateWordWithTranslations(word, translations)
                },
            )
        }
    }
}
