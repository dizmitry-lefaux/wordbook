package com.dkat.wordbook.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dkat.wordbook.viewModel.BooksScreenViewModel
import com.dkat.wordbook.viewModel.EditLanguageViewModel
import com.dkat.wordbook.viewModel.EditWordState
import com.dkat.wordbook.viewModel.EditWordViewModel
import com.dkat.wordbook.viewModel.MainViewModel
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.Word_B
import com.dkat.wordbook.ui.compose.screen.Screen
import com.dkat.wordbook.ui.compose.screen.books.BooksScreen
import com.dkat.wordbook.ui.compose.screen.home.HomeScreen
import com.dkat.wordbook.ui.compose.screen.popup.EditLanguagePopupScreen
import com.dkat.wordbook.ui.compose.screen.popup.EditSourcePopupScreen
import com.dkat.wordbook.ui.compose.screen.session.SessionScreen
import com.dkat.wordbook.ui.compose.screen.words.WordsScreen
import com.dkat.wordbook.ui.compose.screen.popup.EditWordWithTranslationsPopupScreen
import com.dkat.wordbook.viewModel.EditSourceViewModel

@Composable
fun WordbookNavHost(
    navController: NavHostController,
    viewModel: MainViewModel,
    editWordViewModel: EditWordViewModel,
    editLanguageViewModel: EditLanguageViewModel,
    editSourceViewModel: EditSourceViewModel,
    booksScreenViewModel: BooksScreenViewModel,
    modifier: Modifier
) {
    val sources by viewModel.sources.collectAsStateWithLifecycle()
    val sourcesWithWords by viewModel.sourcesWithWords.collectAsStateWithLifecycle()
    val wordsWithTranslations by viewModel.wordsWithTranslations.collectAsStateWithLifecycle()
    val languages by viewModel.languages.collectAsStateWithLifecycle()
    val sessionWords by viewModel.sessionWords.collectAsStateWithLifecycle()

    val editWordState by editWordViewModel.editWordState.collectAsStateWithLifecycle()
    val editLanguageState by editLanguageViewModel.editLanguageState.collectAsStateWithLifecycle()
    val editSourceState by editSourceViewModel.editSourceState.collectAsStateWithLifecycle()

    val booksScreenIsBooksOpen by booksScreenViewModel.isBooksOpen.collectAsStateWithLifecycle()
    val booksScreenIsLanguagesOpen by booksScreenViewModel.isLanguagesOpen.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                navController = navController,
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
                updateEditWordState = { editWordState: EditWordState ->
                    editWordViewModel.updateEditWordState(editWordState)
                },
                updateSourceState = { source: Source ->
                    editSourceViewModel.updateEditSourceState(source)
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
        composable(route = Screen.Words.route) {
            WordsScreen(
                navController = navController,
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
                updateEditWordState = { editWordState: EditWordState ->
                    editWordViewModel.updateEditWordState(editWordState)
                },
                wordsWithTranslations = wordsWithTranslations,
            )
        }
        composable(route = Screen.Books.route) {
            BooksScreen(
                navController = navController,
                isBooksOpen = booksScreenIsBooksOpen,
                isLanguagesOpen = booksScreenIsLanguagesOpen,
                openBooks = { booksScreenViewModel.openBooks() },
                openLanguages = { booksScreenViewModel.openLanguages() },
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
                updateEditWordState = { editWordState: EditWordState ->
                    editWordViewModel.updateEditWordState(editWordState)
                },
                updateLanguageState = { language: Language ->
                    editLanguageViewModel.updateEditLanguageState(language)
                },
                updateSourceState = { source: Source ->
                    editSourceViewModel.updateEditSourceState(source)
                }
            )
        }
        composable(route = Screen.EditWord.route) {
            EditWordWithTranslationsPopupScreen(
                navController = navController,
                editWordState = editWordState,
                editWordWithTranslations = { word: Word_B, translations: List<Translation> ->
                    viewModel.updateWordWithTranslations(word, translations)
                },
            )
        }
        composable(route = Screen.EditLanguage.route) {
            EditLanguagePopupScreen(
                navController = navController,
                editLanguageState = editLanguageState,
                editLanguage = { language: Language ->
                    viewModel.updateLanguage(language)
                },
            )
        }
        composable(route = Screen.EditSource.route) {
            EditSourcePopupScreen(
                navController = navController,
                editSourceState = editSourceState,
                sources = sources,
                languages = languages,
                editSource = { source: Source ->
                    viewModel.updateSource(source)
                },
            )
        }
    }
}
