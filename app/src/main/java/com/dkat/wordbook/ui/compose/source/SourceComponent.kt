package com.dkat.wordbook.ui.compose.source

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word_B
import com.dkat.wordbook.ui.compose.reusable.EditableDeletableItem
import com.dkat.wordbook.ui.compose.reusable.ExpandableSection
import com.dkat.wordbook.ui.compose.screen.Screen
import com.dkat.wordbook.ui.compose.word.WordsWithTranslationsList
import com.dkat.wordbook.viewModel.EditWordState

@Composable
fun ExpandableSourceItem(
    navController: NavController,
    onDeleteSourceClick: (source: Source) -> Unit,
    onDeleteWordClick: (word: Word_B) -> Unit,
    source: Source,
    updateSourceState: (source: Source) -> Unit,
    wordsWithTranslations: List<WordWithTranslations>?,
    readSourceById: (sourceId: Int) -> Source,
    updateEditWordState: (editWordState: EditWordState) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        ExpandableSection(
            modifier = modifier.fillMaxWidth(),
            title = source.name,
            isHideTitleOnExpand = true,
        ) {
            Column {
                SourceItem(
                    navController = navController,
                    modifier = modifier,
                    source = source,
                    updateSourceState = updateSourceState,
                    onDeleteSourceClick = onDeleteSourceClick,
                    wordsWithTranslations = wordsWithTranslations,
                    onDeleteWordClick = onDeleteWordClick,
                    readSource = readSourceById,
                    updateEditWordState = updateEditWordState,
                )
                HorizontalDivider(thickness = 8.dp)
            }
        }
    }
}

@Composable
private fun SourceItem(
    navController: NavController,
    modifier: Modifier,
    source: Source,
    onDeleteSourceClick: ((source: Source) -> Unit)?,
    updateSourceState: (source: Source) -> Unit,
    wordsWithTranslations: List<WordWithTranslations>?,
    onDeleteWordClick: ((word: Word_B) -> Unit)?,
    readSource: (sourceId: Int) -> Source,
    updateEditWordState: (editWordState: EditWordState) -> Unit,
) {
    EditableDeletableItem(navController = navController,
                          editRoute = Screen.EditSource.route,
                          titleValue = source.name,
                          editObject = source,
                          updateEditObjectState = updateSourceState,
                          editDescription = "edit source",
                          deleteObject = source,
                          onDeleteObjectClick = onDeleteSourceClick,
                          deleteDescription = "delete language",
                          additionalContent = {
                              HorizontalDivider(thickness = 2.dp)
                              WordsWithTranslationsList(
                                  navController = navController,
                                  wordsWithTranslations = wordsWithTranslations,
                                  onDeleteWordClick = onDeleteWordClick,
                                  readSourceById = readSource,
                                  updateEditWordState = updateEditWordState,
                                  modifier = modifier,
                              )
                          },
                          modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun SourceItemPreview() {
    SourceItem(
        navController = rememberNavController(),
        modifier = Modifier,
        source = PreviewData.source1,
        wordsWithTranslations = PreviewData.wordsWithTranslations,
        onDeleteSourceClick = {},
        onDeleteWordClick = {},
        readSource = { _ -> Source() },
        updateEditWordState = { _ -> },
        updateSourceState = { _ -> },
    )
}

@Preview(showBackground = true)
@Composable
fun ExpandableSourceItemPreview() {
    ExpandableSourceItem(
        navController = rememberNavController(),
        modifier = Modifier,
        source = PreviewData.source1,
        wordsWithTranslations = PreviewData.wordsWithTranslations,
        onDeleteWordClick = {},
        onDeleteSourceClick = {},
        readSourceById = { _ -> Source() },
        updateEditWordState = { _ -> },
        updateSourceState = { _ -> },
    )
}
