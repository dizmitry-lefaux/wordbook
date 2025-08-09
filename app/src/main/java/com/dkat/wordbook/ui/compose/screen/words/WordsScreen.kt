package com.dkat.wordbook.ui.compose.screen.words

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.SourceAndOrder
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word
import com.dkat.wordbook.ui.compose.reusable.EntityDropdownMenu
import com.dkat.wordbook.ui.compose.reusable.ExpandableSection
import com.dkat.wordbook.ui.compose.word.InputWordWithTranslations
import com.dkat.wordbook.ui.compose.word.WordsList
import com.dkat.wordbook.viewModel.screen.EditableWordState
import kotlinx.coroutines.launch
import java.lang.Thread.sleep

private const val TAG = "WordsScreen"

@Composable
fun WordsScreen(
    navController: NavController,
    sourcesAndOrder: List<SourceAndOrder>,
    readSource: (id: Int) -> Source,
    updateSelectedSource: (source: Source) -> Unit,
    selectedSourceState: Source,
    readWords: (sourceId: Int) -> List<WordWithTranslations>,
    createWordWithTranslations: (word: Word, translations: List<Translation>) -> Unit,
    updateEditableWordState: (editableWordState: EditableWordState) -> Unit,
    onDeleteWordItemClick: (word: Word) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedSource by remember { mutableStateOf(selectedSourceState) }
    var selectedSourceId by remember { mutableStateOf<Int?>(selectedSourceState.id) }
    var selectedSourceLabel by remember {
        mutableStateOf(
            selectedSource.name.ifEmpty { "Select source" }
        )
    }
    var isSelectSourceError by remember { mutableStateOf(false) }
    var sources = sourcesAndOrder
        .sortedWith { it1, it2 -> it1.sourceOrder.order - it2.sourceOrder.order }
        .map { it -> it.source }

    // workaround for reorderable columns: mutableStateOf works not properly with lists
    // need to re-compose words list on source select
    var recomposeTrigger by remember { mutableStateOf(true) }
    var coroutineScope = rememberCoroutineScope()

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxSize()
    ) {
        EntityDropdownMenu(
            list = sources,
            defaultValue = selectedSourceLabel,
            onSelect = {
                selectedSourceId = it.id
                selectedSourceLabel = it.name
                selectedSource = readSource(selectedSourceId!!)
                updateSelectedSource(selectedSource)
                recomposeTrigger = !recomposeTrigger
            },
            resetErrorStateOnClick = { isSelectSourceError = it },
        )
        HorizontalDivider(thickness = 4.dp, color = Color.Black)

        ExpandableSection(
            modifier = modifier.fillMaxWidth(),
            // TODO: move to string resources
            title = "Add new word",
            isHideTitleOnExpand = true,
        ) {
            InputWordWithTranslations(
                createWordWithTranslations = createWordWithTranslations,
                onAddEvent = {
                    coroutineScope.launch {
                        recomposeTrigger = !recomposeTrigger
                        sleep(100L)
                    }
                },
                source = selectedSource,
            )
        }
        HorizontalDivider(thickness = 4.dp, color = Color.Black)
        if (recomposeTrigger) {
            WordsList(
                navController = navController,
                wordsWithTranslations = readWords(selectedSourceId!!),
                onDeleteWordClick = onDeleteWordItemClick,
                onDeleteEvent = {
                    coroutineScope.launch {
                        recomposeTrigger = !recomposeTrigger
                        sleep(100L)
                    }
                },
                readSourceById = readSource,
                updateEditableWord = updateEditableWordState,
                modifier = modifier,
            )
        } else {
            WordsList(
                navController = navController,
                wordsWithTranslations = readWords(selectedSourceId!!),
                onDeleteWordClick = onDeleteWordItemClick,
                onDeleteEvent = {
                    coroutineScope.launch {
                        recomposeTrigger = !recomposeTrigger
                        sleep(100L)
                    }
                },
                readSourceById = readSource,
                updateEditableWord = updateEditableWordState,
                modifier = modifier,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WordsScreenPreview() {
    WordsScreen(
        navController = rememberNavController(),
        sourcesAndOrder = PreviewData.sourceAndOrderList,
        readSource = { _ -> Source() },
        updateSelectedSource = { _ -> },
        selectedSourceState = PreviewData.source1,
        readWords = { _ -> PreviewData.wordsWithTranslations},
        createWordWithTranslations = { _, _ -> },
        updateEditableWordState = { _ -> },
        onDeleteWordItemClick = {},
    )
}
