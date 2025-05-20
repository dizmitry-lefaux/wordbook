package com.dkat.wordbook.ui.compose.source

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.SourceWithWords
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word_B
import com.dkat.wordbook.viewModel.EditWordState

private const val TAG = "ListOfSources"

@Composable
fun ListOfSources(
    navController: NavController,
    sourcesWithWords: List<SourceWithWords>,
    wordsWithTranslations: List<WordWithTranslations>,
    onDeleteWordItemClick: (word: Word_B) -> Unit,
    onDeleteSourceItemClick: (source: Source) -> Unit,
    readSource: (sourceId: Int) -> Source,
    updateEditWordState: (editWordState: EditWordState) -> Unit,
    updateSourceState: (source: Source) -> Unit,
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
                    navController = navController,
                    onDeleteSourceClick = onDeleteSourceItemClick,
                    onDeleteWordClick = onDeleteWordItemClick,
                    source = source,
                    wordsWithTranslations = mapOfSources[source],
                    readSourceById = readSource,
                    updateEditWordState = updateEditWordState,
                    updateSourceState = updateSourceState,
                )
                HorizontalDivider(thickness = 8.dp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListOfSourcesPreview() {
    ListOfSources(
        navController = rememberNavController(),
        sourcesWithWords = PreviewData.sourcesWithWords,
        wordsWithTranslations = PreviewData.wordsWithTranslations,
        onDeleteWordItemClick = {},
        onDeleteSourceItemClick = {},
        readSource = { _ -> Source() },
        updateEditWordState = { _ -> },
        updateSourceState = { _ -> },
    )
}
