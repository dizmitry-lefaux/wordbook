package com.dkat.wordbook.ui.compose.screen.books

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.data.entity.LanguageAndOrder
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.SourceAndOrder
import com.dkat.wordbook.ui.compose.reusable.PillData
import com.dkat.wordbook.ui.compose.reusable.PillSwitch

@Composable
fun BooksScreen(
    navController: NavController,
    isBooksOpen: Boolean,
    isLanguagesOpen: Boolean,
    openBooks: () -> Unit,
    openLanguages: () -> Unit,

    languages: List<LanguageAndOrder>,
    createLanguage: (language: Language) -> Unit,
    deleteLanguage: (language: Language) -> Unit,
    readLanguages: () -> List<LanguageAndOrder>,
    updateLanguageState: (language: Language) -> Unit,
    updateLanguagesOrder: (languages: List<LanguageAndOrder>) -> Unit,

    createSource: (source: Source) -> Unit,
    readSources: () -> List<SourceAndOrder>,
    deleteSource: (source: Source) -> Unit,
    updateSourceState: (source: Source) -> Unit,
    updateSourcesOrder: (sources: List<SourceAndOrder>) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Column {
            PillSwitch(
                pills = listOf(
                    // TODO: move to string resources
                    PillData(title = "BOOKS",
                             isSelected = isBooksOpen,
                             onClick = {
                                 if (!isBooksOpen) {
                                     openBooks()
                                 }
                             }
                    ),
                    // TODO: move to string resources
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
                SourcesPillScreen(modifier = modifier,
                                  createSource = createSource,
                                  languages = languages.map { it -> it.language },
                                  navController = navController,
                                  deleteSource = deleteSource,
                                  readSources = readSources,
                                  updateSourceState = updateSourceState,
                                  updateSourcesOrder = updateSourcesOrder
                )
            }
            if (!isBooksOpen) {
                LanguagesPillScreen(modifier = modifier,
                                    createLanguage = createLanguage,
                                    navController = navController,
                                    deleteLanguage = deleteLanguage,
                                    readLanguages = readLanguages,
                                    updateLanguageState = updateLanguageState,
                                    updateLanguagesOrder = updateLanguagesOrder
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
        languages = PreviewData.languageAndOrderList,
        deleteSource = {},
        createSource = {},
        createLanguage = {},
        deleteLanguage = {},
        readLanguages = { -> emptyList() },
        updateLanguagesOrder = { _ -> },
        readSources = { -> emptyList() },
        updateLanguageState = { _ -> },
        updateSourceState = { _ -> },
        updateSourcesOrder = {},
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
        languages = PreviewData.languageAndOrderList,
        deleteSource = {},
        createSource = {},
        createLanguage = {},
        deleteLanguage = {},
        readLanguages = { -> emptyList() },
        updateLanguagesOrder = { _ -> },
        readSources = { -> emptyList() },
        updateLanguageState = { _ -> },
        updateSourceState = { _ -> },
        updateSourcesOrder = {},
        modifier = Modifier
    )
}
