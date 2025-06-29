package com.dkat.wordbook.ui.compose.word

import android.annotation.SuppressLint
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.Word
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.viewModel.screen.EditableWordState
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

private const val TAG = "WordsWithTranslationsList"

@SuppressLint("UnrememberedMutableState")
@Composable
fun WordsWithTranslationsList(
    navController: NavController,
    wordsWithTranslations: List<WordWithTranslations>,
    onDeleteWordClick: ((word: Word) -> Unit)?,
    readSourceById: (sourceId: Int) -> Source,
    updateEditableWord: (editableWordState: EditableWordState) -> Unit,
    modifier: Modifier,
) {
    // HERE WE NEED TO SOMEHOW CHANGE REFERENCE TO THE LIST INSIDE OF mutableStateOf()
    var list by remember { mutableStateOf(wordsWithTranslations) }
    var lazyListState = rememberLazyListState()
    val reorderableLazyColumnState = rememberReorderableLazyListState(lazyListState) { from, to ->
        list = list.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
    }

    Column {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState,
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(list, key = { _, item -> item.word.id }) { index, item ->
                ReorderableItem(reorderableLazyColumnState, item.word.id) {
                    val interactionSource = remember { MutableInteractionSource() }

                    Card(
                        onClick = {},
                        modifier = Modifier
                            .draggableHandle()
                            .semantics {
                                customActions = listOf(
                                    CustomAccessibilityAction(
                                        label = "Move Up",
                                        action = {
                                            if (index > 0) {
                                                list = list.toMutableList().apply {
                                                    add(index - 1, removeAt(index))
                                                }
                                                true
                                            } else {
                                                false
                                            }
                                        }
                                    ),
                                    CustomAccessibilityAction(
                                        label = "Move Down",
                                        action = {
                                            if (index < list.size - 1) {
                                                list = list.toMutableList().apply {
                                                    add(index + 1, removeAt(index))
                                                }
                                                true
                                            } else {
                                                false
                                            }
                                        }
                                    ),
                                )
                            },
                        interactionSource = interactionSource
                    ) {
                        WordWithTranslationsCard(
                            navController = navController,
                            wordWithTranslations = item,
                            deleteWord = onDeleteWordClick,
                            readSourceById = readSourceById,
                            updateEditableWordState = updateEditableWord,
                            modifier = modifier
                                .draggableHandle(
                                    interactionSource = interactionSource
                                )
                                .clearAndSetSemantics { },
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WordsWithTranslationsListPreview(
) {
    WordsWithTranslationsList(
        navController = rememberNavController(),
        wordsWithTranslations = PreviewData.wordsWithTranslations,
        updateEditableWord = { _ -> },
        onDeleteWordClick = {},
        readSourceById = { _ -> Source() },
        modifier = Modifier
    )
}
