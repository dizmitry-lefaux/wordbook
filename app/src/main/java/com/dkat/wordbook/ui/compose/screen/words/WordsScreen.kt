package com.dkat.wordbook.ui.compose.screen.words

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.dkat.wordbook.data.entity.Word_B
import com.dkat.wordbook.ui.compose.reusable.EntityDropdownMenu
import com.dkat.wordbook.ui.compose.reusable.ExpandableSection
import com.dkat.wordbook.ui.compose.word.InputWordWithTranslations
import com.dkat.wordbook.ui.compose.word.WordsWithTranslationsList
import com.dkat.wordbook.viewModel.screen.EditableWordState

@Composable
fun WordsScreen(
    navController: NavController,
    sources: List<Source>,
    readSource: (id: Int) -> Source,
    updateSelectedSource: (source: Source) -> Unit,
    selectedSourceState: Source,
    wordsWithTranslations: List<WordWithTranslations>,
    createWordWithTranslations: (word: Word_B, translations: List<Translation>) -> Unit,
    updateEditableWordState: (editableWordState: EditableWordState) -> Unit,
    onDeleteWordItemClick: (word: Word_B) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedSource by remember { mutableStateOf<Source?>(selectedSourceState) }
    var selectedSourceId by remember { mutableStateOf<Int?>(selectedSourceState.id) }
    var selectedSourceLabel by remember { mutableStateOf(
        if (selectedSource?.name?.isEmpty() == true) "Select source"
        else selectedSource!!.name
    ) }
    var isSelectSourceError by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()),
           verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        EntityDropdownMenu(
            list = sources,
            defaultValue = selectedSourceLabel,
            onSelect = {
                selectedSourceId = it.id
                selectedSourceLabel = it.name
                selectedSource = readSource(selectedSourceId!!)
                updateSelectedSource(selectedSource!!)
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
        WordsWithTranslationsList(
            navController = navController,
            wordsWithTranslations = wordsWithTranslations.filter { wordWithTranslations ->
                wordWithTranslations.word.sourceId == selectedSourceId
            }.toList(),
            onDeleteWordClick = onDeleteWordItemClick,
            readSourceById = readSource,
            updateEditableWord = updateEditableWordState,
            modifier = modifier,
        )
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
