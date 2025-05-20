package com.dkat.wordbook.ui.compose.screen.books

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dkat.wordbook.viewModel.EditWordState
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.SourceWithWords
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word_B
import com.dkat.wordbook.ui.compose.reusable.PillData
import com.dkat.wordbook.ui.compose.reusable.PillSwitch
import com.dkat.wordbook.ui.compose.reusable.TitleText
import com.dkat.wordbook.ui.compose.source.InputSource
import com.dkat.wordbook.ui.compose.source.ListOfSources

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
    onDeleteSourceItemClick: (source: Source) -> Unit,
    updateSourceState: (source: Source) -> Unit,
    onDeleteWordItemClick: (word: Word_B) -> Unit,
    createSource: (source: Source) -> Unit,
    createLanguage: (language: Language) -> Unit,
    onDeleteLanguageItemClick: (language: Language) -> Unit,
    readSource: (sourceId: Int) -> Source,
    updateEditWordState: (editWordState: EditWordState) -> Unit,
    updateLanguageState: (language: Language) -> Unit,
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
        Column(modifier = modifier.verticalScroll(rememberScrollState())) {
            if (isBooksOpen) {
                // TODO: Move text to string resources
                TitleText(text = "Sources")
                InputSource(
                    createSource = createSource,
                    languages = languages,
                    sources = sourcesWithWords.map { it.source }.toList()
                )
                ListOfSources(
                    navController = navController,
                    onDeleteWordItemClick = onDeleteWordItemClick,
                    onDeleteSourceItemClick = onDeleteSourceItemClick,
                    modifier = modifier,
                    sourcesWithWords = sourcesWithWords,
                    wordsWithTranslations = wordsWithTranslations,
                    readSource = readSource,
                    updateEditWordState = updateEditWordState,
                    updateSourceState = updateSourceState
                )
            }
            if (!isBooksOpen) {
                // TODO: Move text to string resources
                TitleText(text = "Languages")
                InputLanguage(createLanguage = createLanguage, languages = languages)
                ListOfLanguages(
                    navController = navController,
                    languages = languages,
                    onDeleteLanguageItemClick = onDeleteLanguageItemClick,
                    updateLanguageState = updateLanguageState,
                    modifier = modifier
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
        onDeleteSourceItemClick = {},
        onDeleteWordItemClick = {},
        createSource = {},
        createLanguage = {},
        onDeleteLanguageItemClick = {},
        readSource = { _ -> Source() },
        updateEditWordState = { _ -> },
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
        onDeleteSourceItemClick = {},
        onDeleteWordItemClick = {},
        createSource = {},
        createLanguage = {},
        onDeleteLanguageItemClick = {},
        readSource = { _ -> Source() },
        updateEditWordState = { _ -> },
        updateLanguageState = { _ -> },
        updateSourceState = { _ -> },
        modifier = Modifier
    )
}
