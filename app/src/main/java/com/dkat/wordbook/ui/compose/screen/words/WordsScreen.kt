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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word
import com.dkat.wordbook.ui.compose.reusable.EntityDropdownMenu
import com.dkat.wordbook.ui.compose.reusable.ExpandableSection
import com.dkat.wordbook.ui.compose.word.InputWordWithTranslations
import com.dkat.wordbook.ui.compose.word.WordsList
import com.dkat.wordbook.viewModel.screen.EditableWordState

private const val TAG = "WordsScreen"

@Composable
fun WordsScreen(
    navController: NavController,
    sources: List<Source>,
    readSource: (id: Int) -> Source,
    updateSelectedSource: (source: Source) -> Unit,
    selectedSourceState: Source,
    wordsWithTranslations: List<WordWithTranslations>,
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
    // workaround for reorderable columns: mutableStateOf works not properly with lists
    // need to re-compose words list on source select
    var wordsListState by remember { mutableStateOf(true) }

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
                wordsListState = !wordsListState
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
                source = selectedSource,
            )
        }
        HorizontalDivider(thickness = 4.dp, color = Color.Black)
        var filteredWords = wordsWithTranslations.filter { wordWithTranslations ->
            wordWithTranslations.word.sourceId == selectedSourceId
        }.toList()
        if (wordsListState) {
            WordsList(
                navController = navController,
                wordsWithTranslations = filteredWords,
                onDeleteWordClick = onDeleteWordItemClick,
                readSourceById = readSource,
                updateEditableWord = updateEditableWordState,
                modifier = modifier,
            )
        } else {
            WordsList(
                navController = navController,
                wordsWithTranslations = filteredWords,
                onDeleteWordClick = onDeleteWordItemClick,
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
        sources = PreviewData.sources,
        readSource = { _ -> Source() },
        updateSelectedSource = { _ -> },
        selectedSourceState = PreviewData.source1,
        wordsWithTranslations = PreviewData.wordsWithTranslations,
        createWordWithTranslations = { _, _ -> },
        updateEditableWordState = { _ -> },
        onDeleteWordItemClick = {},
    )
}
