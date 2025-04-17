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
import com.dkat.wordbook.ui.compose.screen.Screen
import com.dkat.wordbook.data.entity.Word
import com.dkat.wordbook.data.entity.Word_B
import com.dkat.wordbook.ui.compose.screen.home.HomeScreen
import com.dkat.wordbook.ui.compose.screen.langauge.LanguagesScreen
import com.dkat.wordbook.ui.compose.screen.session.SessionScreen
import com.dkat.wordbook.ui.compose.screen.source.SourcesScreen

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
                sources = sources,
                onDeleteWordItemClick = { word: Word_B ->
                    viewModel.deleteWord(word)
                },
                addWord = { word: Word_B ->
                    viewModel.createWord(word)
                },
                addTranslation = { translation: Translation ->
                    viewModel.createTranslation(translation)
                },
                onClickMigrateSources = {
                    viewModel.migrateSources()
                },
                onClickMigrateLanguages = {
                    viewModel.migrateLanguages()
                },
                onClickMigrateWords = {
                    viewModel.migrateWords()
                },
                onClickMigrateTranslations = {
                    viewModel.migrateTranslations()
                },
                onDeleteSourceItemClick = { source: Source ->
                    viewModel.deleteSource(source)
                },
                sourcesWithWords = sourcesWithWords,
                wordsWithTranslations = wordsWithTranslations,
            )
        }
        composable(route = Screen.Sources.route) {
            SourcesScreen(
                onDeleteSourceItemClick = { source: Source ->
                    viewModel.deleteSource(source)
                },
                onDeleteWordItemClick = { word: Word_B ->
                    viewModel.deleteWord(word)
                },
                createSource = { source: Source ->
                    viewModel.createSource(source)
                },
                sourcesWithWords = sourcesWithWords,
                wordsWithTranslations = wordsWithTranslations,
                languages = languages,
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
        composable(route = Screen.Languages.route) {
            LanguagesScreen(
                languages = languages,
                createLanguage = { language: Language ->
                    viewModel.createLanguage(language)
                },
                onDeleteLanguageItemClick = { language: Language ->
                    viewModel.deleteLanguage(language)
                }
            )
        }
    }
}
