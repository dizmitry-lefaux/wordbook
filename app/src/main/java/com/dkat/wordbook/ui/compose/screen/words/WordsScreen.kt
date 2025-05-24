package com.dkat.wordbook.ui.compose.screen.words

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.dkat.wordbook.viewModel.EditWordState
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word_B
import com.dkat.wordbook.ui.compose.reusable.EntityDropdownMenu
import com.dkat.wordbook.ui.compose.reusable.ExpandableSection
import com.dkat.wordbook.ui.compose.word.InputWordWithTranslations
import com.dkat.wordbook.ui.compose.word.WordsWithTranslationsList

@Composable
fun WordsScreen(
    navController: NavController,
    sources: List<Source>,
    wordsWithTranslations: List<WordWithTranslations>,
    readSource: (id: Int) -> Source,
    createWordWithTranslations: (word: Word_B, translations: List<Translation>) -> Unit,
    updateEditWordState: (editWordState: EditWordState) -> Unit,
    onDeleteWordItemClick: (word: Word_B) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedSource by remember { mutableStateOf<Source?>(null) }
    var selectedSourceId by remember { mutableStateOf<Int?>(null) }
    var isSelectSourceError by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()),
           verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        EntityDropdownMenu(
            list = sources,
            defaultValue = "Select source",
            onSelect = {
                selectedSourceId = it.id
                selectedSource = readSource(selectedSourceId!!)
            },
            resetErrorStateOnClick = { isSelectSourceError = it },
        )
        HorizontalDivider(thickness = 4.dp, color = Color.Black)

        ExpandableSection(
            modifier = modifier.fillMaxWidth(),
            // TODO: move to string resources
            title = "Add new source",
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
            updateEditableWord = updateEditWordState,
            modifier = modifier,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WordsScreenPreview() {
    WordsScreen(
        navController = rememberNavController(),
        wordsWithTranslations = PreviewData.wordsWithTranslations,
        sources = PreviewData.sources,
        readSource = { _ -> Source() },
        createWordWithTranslations = { _, _ -> },
        updateEditWordState = { _ -> },
        onDeleteWordItemClick = {},
    )
}
