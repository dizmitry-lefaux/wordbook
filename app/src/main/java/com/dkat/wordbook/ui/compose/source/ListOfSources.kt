package com.dkat.wordbook.ui.compose.source

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dkat.wordbook.data.PreviewData
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
    readSource: (sourceId: Int) -> Source,
    updateWordWithTranslations: (word: Word_B, translations: List<Translation>) -> Unit,
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
                    wordsWithTranslations = mapOfSources[source],
                    readSourceById = readSource,
                    updateWordWithTranslations = updateWordWithTranslations,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListOfSourcesPreview() {
    ListOfSources(
        sourcesWithWords = PreviewData.sourcesWithWords,
        wordsWithTranslations = PreviewData.wordsWithTranslations,
        onDeleteWordItemClick = {},
        onDeleteSourceItemClick = {},
        readSource = { _ -> Source() },
        updateWordWithTranslations = { _, _ -> },
    )
}
