package com.dkat.wordbook.ui.compose.screen.books

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
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.data.entity.LanguageAndOrder
import com.dkat.wordbook.ui.compose.reusable.getCustomAccessibilityActions
import com.dkat.wordbook.util.ListUtils
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

private const val TAG = "LazyLanguagesList"

@Composable
fun LanguagesList(
    navController: NavController,
    languages: List<LanguageAndOrder>,
    deleteLanguage: (language: Language) -> Unit,
    onDeleteEvent: () -> Unit,
    onMoveEvent: () -> Unit,
    updateLanguageState: (language: Language) -> Unit,
    updateLanguagesOrder: (languages: List<LanguageAndOrder>) -> Unit,
    modifier: Modifier = Modifier
) {
    var list by remember { mutableStateOf(languages) }
    var lazyListState = rememberLazyListState()
    val reorderableLazyColumnState = rememberReorderableLazyListState(lazyListState) { from, to ->
        list = list.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
    }
    var startListState by remember { mutableStateOf(languages) }
    var stopListState by remember { mutableStateOf(languages) }

    Column {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState,
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(items = list,
                         key = { _, language -> language.languageOrder.order }) { index, language ->
                ReorderableItem(reorderableLazyColumnState, language.languageOrder.order) {
                    val interactionSource = remember { MutableInteractionSource() }

                    Card(
                        onClick = {},
                        modifier = Modifier
                            .draggableHandle()
                            .semantics {
                                customActions = getCustomAccessibilityActions(
                                    index = index,
                                    list = languages
                                )
                            },
                        interactionSource = interactionSource
                    ) {
                        LanguageItem(
                            navController = navController,
                            language = language.language,
                            deleteLanguage = deleteLanguage,
                            onDeleteEvent = {
                                onDeleteEvent()
                            },
                            updateLanguageState = updateLanguageState,
                            modifier = modifier
                                .draggableHandle(
                                    interactionSource = interactionSource,
                                    onDragStarted = {
                                        startListState = list
                                    },
                                    onDragStopped = {
                                        stopListState = list
                                        var reorderedElements = ListUtils.getSwappedElements(
                                            startListState, stopListState
                                        )
                                        if (!reorderedElements.isEmpty()) {
                                            updateLanguagesOrder(stopListState)
                                        }
                                        onMoveEvent()
                                    }
                                )
                                .clearAndSetSemantics { }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LazyLanguagesListPreview() {
    LanguagesList(
        navController = rememberNavController(),
        languages = PreviewData.languageAndOrderList,
        deleteLanguage = {},
        onDeleteEvent = {},
        onMoveEvent = {},
        updateLanguageState = {},
        updateLanguagesOrder = { _ -> },
        modifier = Modifier
    )
}
