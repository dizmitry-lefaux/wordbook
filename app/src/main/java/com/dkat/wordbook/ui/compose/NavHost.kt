package com.dkat.wordbook.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.Word_B
import com.dkat.wordbook.ui.compose.screen.Screen
import com.dkat.wordbook.ui.compose.screen.books.BooksScreen
import com.dkat.wordbook.ui.compose.screen.home.HomeScreen
import com.dkat.wordbook.ui.compose.screen.popup.EditLanguagePopupScreen
import com.dkat.wordbook.ui.compose.screen.popup.EditSourcePopupScreen
import com.dkat.wordbook.ui.compose.screen.popup.EditWordWithTranslationsPopupScreen
import com.dkat.wordbook.ui.compose.screen.session.SessionScreen
import com.dkat.wordbook.ui.compose.screen.words.WordsScreen
import com.dkat.wordbook.viewModel.data.LanguageViewModel
import com.dkat.wordbook.viewModel.data.MainViewModel
import com.dkat.wordbook.viewModel.data.SourceViewModel
import com.dkat.wordbook.viewModel.data.WordViewModel
import com.dkat.wordbook.viewModel.screen.BooksScreenViewModel
import com.dkat.wordbook.viewModel.screen.EditLanguagePopupScreenViewModel
import com.dkat.wordbook.viewModel.screen.EditSourcePopupScreenViewModel
import com.dkat.wordbook.viewModel.screen.EditWordState
import com.dkat.wordbook.viewModel.screen.EditWordViewModel
import com.dkat.wordbook.viewModel.screen.WordsScreenViewModel

@Composable
fun WordbookNavHost(
    navController: NavHostController,
    viewModel: MainViewModel,
    languageViewModel: LanguageViewModel,
    wordViewModel: WordViewModel,
    sourceViewModel: SourceViewModel,
    editWordViewModel: EditWordViewModel,
    editLanguageViewModel: EditLanguagePopupScreenViewModel,
    editSourceViewModel: EditSourcePopupScreenViewModel,
    booksScreenViewModel: BooksScreenViewModel,
    wordsScreenViewModel: WordsScreenViewModel,
    modifier: Modifier
) {
    val sources by sourceViewModel.sources.collectAsStateWithLifecycle()
    val sourcesWithWords by sourceViewModel.sourcesWithWords.collectAsStateWithLifecycle()
    val wordsWithTranslations by wordViewModel.wordsWithTranslations.collectAsStateWithLifecycle()
    val languages by languageViewModel.languages.collectAsStateWithLifecycle()
    val sessionWords by viewModel.sessionWords.collectAsStateWithLifecycle()

    val editWordState by editWordViewModel.editWordState.collectAsStateWithLifecycle()
    val editLanguageState by editLanguageViewModel.editLanguageState.collectAsStateWithLifecycle()
    val editSourceState by editSourceViewModel.editSourceState.collectAsStateWithLifecycle()

    val booksScreenIsBooksOpen by booksScreenViewModel.isBooksOpen.collectAsStateWithLifecycle()
    val booksScreenIsLanguagesOpen by booksScreenViewModel.isLanguagesOpen.collectAsStateWithLifecycle()

    val wordsScreenSelectedSource by wordsScreenViewModel.selectedSource.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                navController = navController,
                onDeleteWordItemClick = { word: Word_B ->
                    wordViewModel.deleteWord(word)
                },
                onDeleteSourceItemClick = { source: Source ->
                    sourceViewModel.deleteSource(source)
                },
                sourcesWithWords = sourcesWithWords,
                wordsWithTranslations = wordsWithTranslations,
                readSource = { id: Int ->
                    sourceViewModel.readSource(id)
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
                    sourceViewModel.readSource(id)
                },
                sources = sources,
                updateSelectedSource = { source: Source ->
                    wordsScreenViewModel.updateSelectedSource(source)
                },
                selectedSourceState = wordsScreenSelectedSource,
                createWordWithTranslations = { word: Word_B, translations: List<Translation> ->
                    wordViewModel.createWordWithTranslations(word, translations)
                },
                onDeleteWordItemClick = { word: Word_B ->
                    wordViewModel.deleteWord(word)
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
                createSource = { source: Source ->
                    sourceViewModel.createSource(source)
                },
                createLanguage = { language: Language ->
                    languageViewModel.createLanguage(language)
                },
                onDeleteWordItemClick = { word: Word_B ->
                    wordViewModel.deleteWord(word)
                },
                onDeleteSourceItemClick = { source: Source ->
                    sourceViewModel.deleteSource(source)
                },
                onDeleteLanguageItemClick = { language: Language ->
                    languageViewModel.deleteLanguage(language)
                },
                readSource = { id: Int ->
                    sourceViewModel.readSource(id)
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
                    wordViewModel.updateWordWithTranslations(word, translations)
                },
            )
        }
        composable(route = Screen.EditLanguage.route) {
            EditLanguagePopupScreen(
                navController = navController,
                editLanguageState = editLanguageState,
                editLanguage = { language: Language ->
                    languageViewModel.updateLanguage(language)
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
                    sourceViewModel.updateSource(source)
                },
            )
        }
    }
}
