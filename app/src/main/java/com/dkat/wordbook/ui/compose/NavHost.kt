package com.dkat.wordbook.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.data.entity.Session
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.Word
import com.dkat.wordbook.ui.compose.screen.Screen
import com.dkat.wordbook.ui.compose.screen.books.BooksScreen
import com.dkat.wordbook.ui.compose.screen.home.HomeScreen
import com.dkat.wordbook.ui.compose.screen.popup.EditLanguagePopupScreen
import com.dkat.wordbook.ui.compose.screen.popup.EditSessionPopupScreen
import com.dkat.wordbook.ui.compose.screen.popup.EditSourcePopupScreen
import com.dkat.wordbook.ui.compose.screen.popup.EditWordWithTranslationsPopupScreen
import com.dkat.wordbook.ui.compose.screen.session.SessionsScreen
import com.dkat.wordbook.ui.compose.screen.words.WordsScreen
import com.dkat.wordbook.viewModel.data.LanguageViewModel
import com.dkat.wordbook.viewModel.data.SessionViewModel
import com.dkat.wordbook.viewModel.data.SourceViewModel
import com.dkat.wordbook.viewModel.data.WordViewModel
import com.dkat.wordbook.viewModel.screen.BooksScreenViewModel
import com.dkat.wordbook.viewModel.screen.EditLanguagePopupScreenViewModel
import com.dkat.wordbook.viewModel.screen.EditSessionPopupScreenViewModel
import com.dkat.wordbook.viewModel.screen.EditableSessionState
import com.dkat.wordbook.viewModel.screen.EditSourcePopupScreenViewModel
import com.dkat.wordbook.viewModel.screen.EditableWordState
import com.dkat.wordbook.viewModel.screen.EditWordPopupScreenViewModel
import com.dkat.wordbook.viewModel.screen.SessionsScreenViewModel
import com.dkat.wordbook.viewModel.screen.WordsScreenViewModel

@Composable
fun WordbookNavHost(
    navController: NavHostController,
    languageViewModel: LanguageViewModel,
    wordViewModel: WordViewModel,
    sourceViewModel: SourceViewModel,
    sessionViewModel: SessionViewModel,
    editWordPopupScreenViewModel: EditWordPopupScreenViewModel,
    editLanguageViewModel: EditLanguagePopupScreenViewModel,
    editSourceViewModel: EditSourcePopupScreenViewModel,
    editSessionViewModel: EditSessionPopupScreenViewModel,
    booksScreenViewModel: BooksScreenViewModel,
    wordsScreenViewModel: WordsScreenViewModel,
    sessionsScreenViewModel: SessionsScreenViewModel,
    modifier: Modifier
) {
    val sources by sourceViewModel.sources.collectAsStateWithLifecycle()
    val sourcesWithWords by sourceViewModel.sourcesWithWords.collectAsStateWithLifecycle()
    val wordsWithTranslations by wordViewModel.wordsWithTranslations.collectAsStateWithLifecycle()
    val languages by languageViewModel.languages.collectAsStateWithLifecycle()
    val sessions by sessionViewModel.sessions.collectAsStateWithLifecycle()

    val editableWordState by editWordPopupScreenViewModel.editableWordState.collectAsStateWithLifecycle()
    val editableLanguageState by editLanguageViewModel.editableLanguageState.collectAsStateWithLifecycle()
    val editableSourceState by editSourceViewModel.editableSourceState.collectAsStateWithLifecycle()
    val editableSessionState by editSessionViewModel.editableSessionState.collectAsStateWithLifecycle()

    val booksScreenIsBooksOpen by booksScreenViewModel.isBooksOpen.collectAsStateWithLifecycle()
    val booksScreenIsLanguagesOpen by booksScreenViewModel.isLanguagesOpen.collectAsStateWithLifecycle()

    val wordsScreenSelectedSource by wordsScreenViewModel.selectedSource.collectAsStateWithLifecycle()

    val selectedSession by sessionViewModel.selectedSession.collectAsStateWithLifecycle()
    val sessionsScreenSessionWords by sessionViewModel.selectedSessionActiveWords.collectAsStateWithLifecycle()
    val sessionsScreenIsSessionOpen by sessionsScreenViewModel.isSessionOpen.collectAsStateWithLifecycle()
    val sessionsScreenIsManageSessionOpen by sessionsScreenViewModel.isManageSessionOpen.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                navController = navController,
                onDeleteWordItemClick = { word: Word ->
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
                updateEditableWordState = { editableWordState: EditableWordState ->
                    editWordPopupScreenViewModel.updateEditableWordState(editableWordState)
                },
                updateSourceState = { source: Source ->
                    editSourceViewModel.updateEditableSourceState(source)
                }
            )
        }
        composable(route = Screen.Session.route) {
            SessionsScreen(
                isSessionOpen = sessionsScreenIsSessionOpen,
                isManageSessionOpen = sessionsScreenIsManageSessionOpen,
                openSession = { sessionsScreenViewModel.openSession() },
                openManageSession = { sessionsScreenViewModel.openManageSession() },

                sources = sources,
                readSourceById = { sourceId: Int ->
                    sourceViewModel.readSource(sourceId)
                },
                sessions = sessions,
                selectedSessionState = selectedSession,
                createSession = { source: Source, session: Session ->
                    sessionViewModel.createSession(source = source, session = session)
                },
                readSession = { id: Int ->
                    sessionViewModel.readSession(id)
                },
                updateSelectedSession = { session: Session ->
                    sessionViewModel.updateSelectedSession(session)
                },
                navController = navController,
                deleteSession = {session: Session ->
                    sessionViewModel.deleteSession(session = session)
                },
                updateEditableSessionState = { editableSessionState: EditableSessionState ->
                    editSessionViewModel.updateEditableSessionState(editableSessionState)
                },
                readSourcesBySessionId = { sessionId: Int ->
                    sessionViewModel.readSourcesBySessionId(sessionId)
                },
                sessionWordsWithTranslations = sessionsScreenSessionWords,
                restartSession = { sessionViewModel.restartSession() },
                updateSession = { sessionViewModel.updateSession() }
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
                createWordWithTranslations = { word: Word, translations: List<Translation> ->
                    wordViewModel.createWordWithTranslations(word, translations)
                },
                onDeleteWordItemClick = { word: Word ->
                    wordViewModel.deleteWord(word)
                },
                updateEditableWordState = { editableWordState: EditableWordState ->
                    editWordPopupScreenViewModel.updateEditableWordState(editableWordState)
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
                deleteWord = { word: Word ->
                    wordViewModel.deleteWord(word)
                },
                deleteSource = { source: Source ->
                    sourceViewModel.deleteSource(source)
                },
                deleteLanguage = { language: Language ->
                    languageViewModel.deleteLanguage(language)
                },
                readSource = { id: Int ->
                    sourceViewModel.readSource(id)
                },
                updateEditableWordState = { editableWordState: EditableWordState ->
                    editWordPopupScreenViewModel.updateEditableWordState(editableWordState)
                },
                updateLanguageState = { language: Language ->
                    editLanguageViewModel.updateEditableLanguageState(language)
                },
                updateSourceState = { source: Source ->
                    editSourceViewModel.updateEditableSourceState(source)
                }
            )
        }
        composable(route = Screen.EditWord.route) {
            EditWordWithTranslationsPopupScreen(
                navController = navController,
                editableWordState = editableWordState,
                editWordWithTranslations = { word: Word, translations: List<Translation> ->
                    wordViewModel.updateWordWithTranslations(word, translations)
                },
            )
        }
        composable(route = Screen.EditLanguage.route) {
            EditLanguagePopupScreen(
                navController = navController,
                editableLanguageState = editableLanguageState,
                editLanguage = { language: Language ->
                    languageViewModel.updateLanguage(language)
                },
            )
        }
        composable(route = Screen.EditSource.route) {
            EditSourcePopupScreen(
                navController = navController,
                editableSourceState = editableSourceState,
                sources = sources,
                languages = languages,
                editSource = { source: Source ->
                    sourceViewModel.updateSource(source)
                },
            )
        }
        composable(route = Screen.EditSession.route) {
            EditSessionPopupScreen(
                navController = navController,
                editableSessionState = editableSessionState,
                sessions = sessions,
                sources = sources,
                editSession = { session: Session, sources: List<Source> ->
                    sessionViewModel.updateSessionWithSources(session, sources)
                }
            )
        }
    }
}
