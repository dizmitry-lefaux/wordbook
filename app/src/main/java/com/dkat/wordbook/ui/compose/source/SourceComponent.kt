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
import com.dkat.wordbook.data.entity.Word
import com.dkat.wordbook.ui.compose.reusable.EditableDeletableItem
import com.dkat.wordbook.ui.compose.reusable.ExpandableSection
import com.dkat.wordbook.ui.compose.screen.Screen
import com.dkat.wordbook.ui.compose.word.WordsList
import com.dkat.wordbook.viewModel.screen.EditableWordState

@Composable
fun ExpandableSourceItem(
    navController: NavController,
    onDeleteSourceClick: (source: Source) -> Unit,
    onDeleteWordClick: (word: Word) -> Unit,
    source: Source,
    updateSourceState: (source: Source) -> Unit,
    wordsWithTranslations: List<WordWithTranslations>,
    readSourceById: (sourceId: Int) -> Source,
    updateEditableWordState: (editableWordState: EditableWordState) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        ExpandableSection(
            modifier = modifier.fillMaxWidth(),
            title = source.name,
            isHideTitleOnExpand = true,
        ) {
            Column {
                EditableDeletableItem(navController = navController,
                                      editRoute = Screen.EditSource.route,
                                      titleValue = source.name,
                                      editableObject = source,
                                      updateEditableObject = updateSourceState,
                                      // TODO: move to string resources
                                      editDescription = "edit source",
                                      deletableObject = source,
                                      deleteObject = onDeleteSourceClick,
                                      // TODO: move to string resources
                                      deleteDescription = "delete language",
                                      additionalContent = {
                                          HorizontalDivider(thickness = 2.dp)
                                          WordsList(
                                              navController = navController,
                                              wordsWithTranslations = wordsWithTranslations,
                                              onDeleteWordClick = onDeleteWordClick,
                                              readSourceById = readSourceById,
                                              updateEditableWord = updateEditableWordState,
                                              modifier = modifier,
                                          )
                                      },
                                      modifier = modifier
                )
            }
        }
    }
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
        updateEditableWordState = { _ -> },
        updateSourceState = { _ -> },
    )
}
