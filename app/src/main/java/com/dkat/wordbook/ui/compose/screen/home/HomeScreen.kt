package com.dkat.wordbook.ui.compose.screen.home

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.SourceWithWords
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word_B
import com.dkat.wordbook.ui.compose.source.ListOfSources
import com.dkat.wordbook.ui.theme.AppTheme

@Composable
fun HomeScreen(
    sources: List<Source>,
    sourcesWithWords: List<SourceWithWords>,
    wordsWithTranslations: List<WordWithTranslations>,
    modifier: Modifier = Modifier,
    addWord: (word: Word_B) -> Long,
    addTranslation: (translation: Translation) -> Long,
    onDeleteWordItemClick: (word: Word_B) -> Unit,
    onDeleteSourceItemClick: (source: Source) -> Unit,
    onClickMigrateSources: () -> Unit,
    onClickMigrateLanguages: () -> Unit,
    onClickMigrateWords: () -> Unit,
    onClickMigrateTranslations: () -> Unit,
    scrollState: ScrollState = rememberScrollState()
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            enabled = false,
            modifier = modifier.padding(4.dp),
            onClick = onClickMigrateSources
        ) {
            Text(
                text = "MIGRATE SOURCES",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Button(
            enabled = false,
            modifier = modifier.padding(4.dp),
            onClick = onClickMigrateLanguages
        ) {
            Text(
                text = "MIGRATE LANGUAGES",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Button(
            enabled = false,
            modifier = modifier.padding(4.dp),
            onClick = onClickMigrateWords
        ) {
            Text(
                text = "MIGRATE WORDS",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Button(
            enabled = false,
            modifier = modifier.padding(4.dp),
            onClick = onClickMigrateTranslations
        ) {
            Text(
                text = "MIGRATE TRANSLATIONS",
                style = MaterialTheme.typography.titleMedium
            )
        }
        InputWord(
            addWord = addWord,
            sources = sources,
            addTranslation = addTranslation,
        )
        HorizontalDivider(thickness = 4.dp, color = Color.Black)
        Column {
            Text(
                text = "Words grouped by sources:",
                modifier = modifier.padding(12.dp),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left
            )
            ListOfSources(
                sourcesWithWords = sourcesWithWords,
                wordsWithTranslations = wordsWithTranslations,
                onDeleteWordItemClick = onDeleteWordItemClick,
                onDeleteSourceItemClick = onDeleteSourceItemClick,
                modifier = modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(
            sourcesWithWords = listOf(
                SourceWithWords(
                    source = Source(
                        id = 1845,
                        name = "Tony Marquez",
                        mainOrigLangId = 5432,
                        mainTranslationLangId = 4679
                    ),
                    words = listOf(
                        Word_B(id = 4428, sourceId = 4887, languageId = 9685, value = "dictas"),
                        Word_B(id = 6426, sourceId = 9615, languageId = 7799, value = "nullam"),
                        Word_B(id = 6426, sourceId = 9615, languageId = 7799, value = "nullam"),
                    )
                )
            ),
            wordsWithTranslations = listOf(
                WordWithTranslations(
                    word = Word_B(id = 4428, sourceId = 4887, languageId = 9685, value = "dictas"),
                    translations = listOf(
                        Translation(
                            id = 5050,
                            wordId = 3924,
                            value = "detraxit",
                            languageId = 2641
                        ),
                        Translation(
                            id = 3907,
                            wordId = 7219,
                            value = "hendrerit",
                            languageId = 5243
                        )
                    )
                ),
                WordWithTranslations(
                    word = Word_B(id = 6426, sourceId = 9615, languageId = 7799, value = "nullam"),
                    translations = listOf(
                        Translation(
                            id = 1393,
                            wordId = 2937,
                            value = "consequat",
                            languageId = 4081
                        ),
                        Translation(id = 6075, wordId = 2539, value = "tellus", languageId = 3243),
                        Translation(
                            id = 6495,
                            wordId = 7035,
                            value = "consequat",
                            languageId = 9676
                        )
                    )
                ),
                WordWithTranslations(
                    word = Word_B(id = 6426, sourceId = 9615, languageId = 7799, value = "nullam"),
                    translations = listOf(
                        Translation(
                            id = 1393,
                            wordId = 2937,
                            value = "consequat",
                            languageId = 4081
                        ),
                        Translation(id = 6075, wordId = 2539, value = "tellus", languageId = 3243)
                    )
                )
            ),
            addWord = {0},
            addTranslation = {0},
            onDeleteWordItemClick = {},
            onClickMigrateSources = {},
            onClickMigrateLanguages = {},
            onClickMigrateWords = {},
            onClickMigrateTranslations = {},
            onDeleteSourceItemClick = {},
            sources = listOf(
                Source(
                    id = 1845,
                    name = "Tony Marquez",
                    mainOrigLangId = 5432,
                    mainTranslationLangId = 4679
                )
            ),
        )
    }
}
