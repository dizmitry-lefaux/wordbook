package com.dkat.wordbook.ui.compose.source

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.SourceWithWords
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word_B

private const val TAG = "ListOfSources"

@Composable
fun ListOfSources(
    sourcesWithWords: List<SourceWithWords>,
    wordsWithTranslations: List<WordWithTranslations>,
    onDeleteWordItemClick: (word: Word_B) -> Unit,
    onDeleteSourceItemClick: (source: Source) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        val mapOfSources: MutableMap<Source, List<WordWithTranslations>> = mutableMapOf()
        for (sourceWithWords in sourcesWithWords) {
            val wordsWithTranslationsSubList: List<WordWithTranslations> =
                wordsWithTranslations.filter { wordWithTranslations ->
                    wordWithTranslations.word.sourceId == sourceWithWords.source.id
                }.toList()
            mapOfSources[sourceWithWords.source] = wordsWithTranslationsSubList
        }
        val sourcesList = mapOfSources.keys.toList()
        sourcesList.forEach { source ->
            Column {
                ExpandableSourceItem(
                    onDeleteSourceClick = onDeleteSourceItemClick,
                    onDeleteWordClick = onDeleteWordItemClick,
                    source = source,
                    wordsWithTranslations = mapOfSources[source]
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListOfSourcesPreview() {
    ListOfSources(
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
                    Translation(id = 5050, wordId = 3924, value = "detraxit", languageId = 2641),
                    Translation(id = 3907, wordId = 7219, value = "hendrerit", languageId = 5243)
                )
            ),
            WordWithTranslations(
                word = Word_B(id = 6426, sourceId = 9615, languageId = 7799, value = "nullam"),
                translations = listOf(
                    Translation(id = 1393, wordId = 2937, value = "consequat", languageId = 4081),
                    Translation(id = 6075, wordId = 2539, value = "tellus", languageId = 3243),
                    Translation(id = 6495, wordId = 7035, value = "consequat", languageId = 9676)
                )
            ),
            WordWithTranslations(
                word = Word_B(id = 6426, sourceId = 9615, languageId = 7799, value = "nullam"),
                translations = listOf(
                    Translation(id = 1393, wordId = 2937, value = "consequat", languageId = 4081),
                    Translation(id = 6075, wordId = 2539, value = "tellus", languageId = 3243)
                )
            )
        ),
        onDeleteWordItemClick = {},
        onDeleteSourceItemClick = {},
    )
}
