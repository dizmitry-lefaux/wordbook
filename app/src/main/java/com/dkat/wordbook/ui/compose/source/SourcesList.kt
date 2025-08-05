package com.dkat.wordbook.ui.compose.source

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
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.SourceAndOrder
import com.dkat.wordbook.ui.compose.reusable.getCustomAccessibilityActions
import com.dkat.wordbook.util.ListUtils
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

private const val TAG = "SourcesList"

@Composable
fun SourcesList(
    navController: NavController,
    sources: List<SourceAndOrder>,
    languages: List<Language>,
    onDeleteSourceItemClick: (source: Source) -> Unit,
    onDeleteEvent: () -> Unit,
    onMoveEvent: () -> Unit,
    updateSourceState: (source: Source) -> Unit,
    updateSourcesOrder: (sources: List<SourceAndOrder>) -> Unit,
    modifier: Modifier = Modifier
) {
    var list by remember { mutableStateOf(sources) }
    var lazyListState = rememberLazyListState()
    val reorderableLazyColumnState = rememberReorderableLazyListState(lazyListState) { from, to ->
        list = list.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
    }
    var startListState by remember { mutableStateOf(sources) }
    var stopListState by remember { mutableStateOf(sources) }

    Column {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState,
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(items = list,
                         key = { _, source -> source.sourceOrder.order }) { index, source ->
                ReorderableItem(reorderableLazyColumnState, source.sourceOrder.order) {
                    val interactionSource = remember { MutableInteractionSource() }

                    Card(
                        onClick = {},
                        modifier = Modifier
                            .draggableHandle()
                            .semantics {
                                customActions = getCustomAccessibilityActions(
                                    index = index,
                                    list = sources
                                )
                            },
                        interactionSource = interactionSource
                    ) {
                        SourceItem(
                            navController = navController,
                            onDeleteSourceClick = onDeleteSourceItemClick,
                            onDeleteEvent = onDeleteEvent,
                            source = source.source,
                            origLanguage = languages.findLast { it.id == source.source.mainOrigLangId }!!,
                            translationLanguage = languages.findLast { it.id == source.source.mainTranslationLangId }!!,
                            updateSourceState = updateSourceState,
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
                                            updateSourcesOrder(stopListState)
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
fun SourcesListPreview() {
    SourcesList(
        navController = rememberNavController(),
        onDeleteSourceItemClick = {},
        sources = PreviewData.sourceAndOrderList,
        onDeleteEvent = {},
        onMoveEvent = {},
        updateSourceState = { _ -> },
        updateSourcesOrder = { _ -> },
        languages = PreviewData.languages,
    )
}
