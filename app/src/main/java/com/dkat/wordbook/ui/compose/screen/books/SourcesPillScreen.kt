package com.dkat.wordbook.ui.compose.screen.books

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.SourceWithWords
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word
import com.dkat.wordbook.ui.compose.reusable.ExpandableSection
import com.dkat.wordbook.ui.compose.source.InputSource
import com.dkat.wordbook.ui.compose.source.SourcesList
import com.dkat.wordbook.viewModel.screen.EditableWordState

@Composable
fun SourcesPillScreen(modifier: Modifier,
                      createSource: (source: Source) -> Unit,
                      languages: List<Language>,
                      sourcesWithWords: List<SourceWithWords>,
                      navController: NavController,
                      deleteWord: (word: Word) -> Unit,
                      deleteSource: (source: Source) -> Unit,
                      wordsWithTranslations: List<WordWithTranslations>,
                      readSource: (sourceId: Int) -> Source,
                      updateEditableWordState: (editableWordState: EditableWordState) -> Unit,
                      updateSourceState: (source: Source) -> Unit
) {
    Column {
        ExpandableSection(
            modifier = modifier.fillMaxWidth(),
            // TODO: move to string resources
            title = "Add new source",
            isHideTitleOnExpand = true,
        ) {
            InputSource(
                createSource = createSource,
                languages = languages,
                sources = sourcesWithWords.map { it.source }.toList()
            )
        }
        HorizontalDivider(thickness = 4.dp, color = Color.Black)
        SourcesList(
            navController = navController,
            onDeleteWordItemClick = deleteWord,
            onDeleteSourceItemClick = deleteSource,
            modifier = modifier,
            sourcesWithWords = sourcesWithWords,
            wordsWithTranslations = wordsWithTranslations,
            readSource = readSource,
            updateEditableWordState = updateEditableWordState,
            updateSourceState = updateSourceState
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SourcesPillScreenComponentPreview() {
    SourcesPillScreen(modifier = Modifier,
                      createSource = { },
                      languages = PreviewData.languages,
                      sourcesWithWords = PreviewData.sourcesWithWords,
                      navController = rememberNavController(),
                      deleteWord = { },
                      deleteSource = { },
                      wordsWithTranslations = PreviewData.wordsWithTranslations,
                      readSource = { _ -> Source() },
                      updateEditableWordState = { },
                      updateSourceState = { }
    )
}
