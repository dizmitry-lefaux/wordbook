package com.dkat.wordbook.ui.compose.screen.books

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.SourceWithWords
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word_B
import com.dkat.wordbook.ui.compose.reusable.PillData
import com.dkat.wordbook.ui.compose.reusable.PillSwitch
import com.dkat.wordbook.ui.compose.screen.language.InputLanguage
import com.dkat.wordbook.ui.compose.screen.language.ListOfLanguages
import com.dkat.wordbook.ui.compose.screen.source.InputSource
import com.dkat.wordbook.ui.compose.source.ListOfSources

@Composable
fun BooksScreen(
    sourcesWithWords: List<SourceWithWords>,
    wordsWithTranslations: List<WordWithTranslations>,
    languages: List<Language>,
    onDeleteSourceItemClick: (source: Source) -> Unit,
    onDeleteWordItemClick: (word: Word_B) -> Unit,
    createSource: (source: Source) -> Unit,
    createLanguage: (language: Language) -> Unit,
    onDeleteLanguageItemClick: (language: Language) -> Unit,
    modifier: Modifier = Modifier
) {

    var isBooksOpen by remember { mutableStateOf(true) }
    var isLanguagesOpen by remember { mutableStateOf(false) }

    Column {
        Column {
            PillSwitch(
                pills = listOf(
                    PillData(title = "BOOKS",
                             onClick = {
                                 if (!isBooksOpen) {
                                     isBooksOpen = true
                                     isLanguagesOpen = false
                                 }
                             }
                    ),
                    PillData(title = "LANGUAGES",
                             onClick = {
                                 if (!isLanguagesOpen) {
                                     isLanguagesOpen = true
                                     isBooksOpen = false
                                 }
                             }
                    )
                )
            )
        }
        Column(modifier = modifier.verticalScroll(rememberScrollState())) {
            if (isBooksOpen) {
                Text(modifier = modifier.padding(16.dp),
                    // TODO: Move text to string resources
                     text = "Sources",
                     style = MaterialTheme.typography.titleLarge,
                     fontWeight = FontWeight.Bold,
                     textAlign = TextAlign.Left
                )
                InputSource(
                    createSource = createSource,
                    languages = languages,
                    sources = sourcesWithWords.map { it.source }.toList()
                )
                ListOfSources(
                    onDeleteWordItemClick = onDeleteWordItemClick,
                    onDeleteSourceItemClick = onDeleteSourceItemClick,
                    modifier = modifier,
                    sourcesWithWords = sourcesWithWords,
                    wordsWithTranslations = wordsWithTranslations
                )
            }
            if (!isBooksOpen) {
                Text(modifier = modifier.padding(16.dp),
                    // TODO: Move text to string resources
                     text = "Languages",
                     style = MaterialTheme.typography.titleLarge,
                     fontWeight = FontWeight.Bold,
                     textAlign = TextAlign.Left
                )
                InputLanguage(createLanguage = createLanguage, languages = languages)
                ListOfLanguages(
                    languages = languages,
                    onDeleteLanguageItemClick = onDeleteLanguageItemClick,
                    modifier = modifier
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BooksScreenPreview() {
    BooksScreen(
        sourcesWithWords = listOf(SourceWithWords(
            source = Source(id = 1845,
                            name = "Tony Marquez",
                            mainOrigLangId = 5432,
                            mainTranslationLangId = 4679
            ),
            words = listOf(
                Word_B(id = 4428,
                       sourceId = 4887,
                       languageId = 9685,
                       value = "dictas"
                ),
                Word_B(id = 6426,
                       sourceId = 9615,
                       languageId = 7799,
                       value = "nullam"
                ),
                Word_B(id = 6426,
                       sourceId = 9615,
                       languageId = 7799,
                       value = "nullam"
                ),
            )
        )
        ), wordsWithTranslations = listOf(
            WordWithTranslations(
                word = Word_B(id = 4428,
                              sourceId = 4887,
                              languageId = 9685,
                              value = "dictas"
                ),
                translations = listOf(
                    Translation(id = 5050,
                                wordId = 3924,
                                value = "detraxit",
                                languageId = 2641
                    ),
                    Translation(id = 3907,
                                wordId = 7219,
                                value = "hendrerit",
                                languageId = 5243
                    )
                )
            ),
            WordWithTranslations(
                word = Word_B(id = 6426,
                              sourceId = 9615,
                              languageId = 7799,
                              value = "nullam"
                ),
                translations = listOf(
                    Translation(id = 1393,
                                wordId = 2937,
                                value = "consequat",
                                languageId = 4081
                    ),
                    Translation(id = 6075,
                                wordId = 2539,
                                value = "tellus",
                                languageId = 3243
                    ),
                    Translation(id = 6495,
                                wordId = 7035,
                                value = "consequat",
                                languageId = 9676
                    )
                )
            ),
            WordWithTranslations(
                word = Word_B(id = 6426,
                              sourceId = 9615,
                              languageId = 7799,
                              value = "nullam"
                ),
                translations = listOf(
                    Translation(id = 1393,
                                wordId = 2937,
                                value = "consequat",
                                languageId = 4081
                    ),
                    Translation(id = 6075,
                                wordId = 2539,
                                value = "tellus",
                                languageId = 3243
                    )
                )
            )
        ),
        languages = listOf(
            Language(id = 4426, name = "English"),
            Language(id = 4427, name = "Russian"),
        ),
        onDeleteSourceItemClick = {},
        onDeleteWordItemClick = {},
        createSource = {},
        createLanguage = {},
        onDeleteLanguageItemClick = {},
        modifier = Modifier
    )
}
