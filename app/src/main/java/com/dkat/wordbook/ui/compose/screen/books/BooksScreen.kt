package com.dkat.wordbook.ui.compose.screen.books

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.SourceWithWords
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word
import com.dkat.wordbook.ui.compose.reusable.PillData
import com.dkat.wordbook.ui.compose.reusable.PillSwitch
import com.dkat.wordbook.viewModel.screen.EditableWordState

@Composable
fun BooksScreen(
    navController: NavController,
    isBooksOpen: Boolean,
    isLanguagesOpen: Boolean,
    openBooks: () -> Unit,
    openLanguages: () -> Unit,

    sourcesWithWords: List<SourceWithWords>,
    wordsWithTranslations: List<WordWithTranslations>,

    languages: List<Language>,
    createLanguage: (language: Language) -> Unit,
    deleteLanguage: (language: Language) -> Unit,
    updateLanguageState: (language: Language) -> Unit,

    createSource: (source: Source) -> Unit,
    readSource: (sourceId: Int) -> Source,
    deleteSource: (source: Source) -> Unit,
    updateSourceState: (source: Source) -> Unit,

    deleteWord: (word: Word) -> Unit,
    updateEditableWordState: (editableWordState: EditableWordState) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Column {
            PillSwitch(
                pills = listOf(
                    PillData(title = "BOOKS",
                             isSelected = isBooksOpen,
                             onClick = {
                                 if (!isBooksOpen) {
                                     openBooks()
                                 }
                             }
                    ),
                    PillData(title = "LANGUAGES",
                             isSelected = isLanguagesOpen,
                             onClick = {
                                 if (!isLanguagesOpen) {
                                     openLanguages()
                                 }
                             }
                    )
                )
            )
        }
        Column {
            if (isBooksOpen) {
                SourcesPillScreenComponent(modifier = modifier,
                                           createSource = createSource,
                                           languages = languages,
                                           sourcesWithWords = sourcesWithWords,
                                           navController = navController,
                                           deleteWord = deleteWord,
                                           deleteSource = deleteSource,
                                           wordsWithTranslations = wordsWithTranslations,
                                           readSource = readSource,
                                           updateEditableWordState = updateEditableWordState,
                                           updateSourceState = updateSourceState
                )
            }
            if (!isBooksOpen) {
                LanguagesPillScreenComponent(modifier = modifier,
                                             createLanguage = createLanguage,
                                             languages = languages,
                                             navController = navController,
                                             deleteLanguage = deleteLanguage,
                                             updateLanguageState = updateLanguageState
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BooksScreenOpenBooksPreview() {
    BooksScreen(
        isBooksOpen = true,
        isLanguagesOpen = false,
        openBooks = {},
        openLanguages = {},
        navController = rememberNavController(),
        sourcesWithWords = PreviewData.sourcesWithWords,
        wordsWithTranslations = PreviewData.wordsWithTranslations,
        languages = PreviewData.languages,
        deleteSource = {},
        deleteWord = {},
        createSource = {},
        createLanguage = {},
        deleteLanguage = {},
        readSource = { _ -> Source() },
        updateEditableWordState = { _ -> },
        updateLanguageState = { _ -> },
        updateSourceState = { _ -> },
        modifier = Modifier
    )
}

@Preview(showBackground = true)
@Composable
fun BooksScreenOpenLanguagesPreview() {
    BooksScreen(
        isBooksOpen = false,
        isLanguagesOpen = true,
        openBooks = {},
        openLanguages = {},
        navController = rememberNavController(),
        sourcesWithWords = PreviewData.sourcesWithWords,
        wordsWithTranslations = PreviewData.wordsWithTranslations,
        languages = PreviewData.languages,
        deleteSource = {},
        deleteWord = {},
        createSource = {},
        createLanguage = {},
        deleteLanguage = {},
        readSource = { _ -> Source() },
        updateEditableWordState = { _ -> },
        updateLanguageState = { _ -> },
        updateSourceState = { _ -> },
        modifier = Modifier
    )
}
