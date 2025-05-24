package com.dkat.wordbook.ui.compose.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dkat.wordbook.viewModel.screen.EditWordState
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.SourceWithWords
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word_B
import com.dkat.wordbook.ui.compose.reusable.TitleText
import com.dkat.wordbook.ui.compose.source.ListOfSources
import com.dkat.wordbook.ui.theme.AppTheme

@Composable
fun HomeScreen(navController: NavController,
               sourcesWithWords: List<SourceWithWords>,
               wordsWithTranslations: List<WordWithTranslations>,
               modifier: Modifier = Modifier,
               onDeleteWordItemClick: (word: Word_B) -> Unit,
               onDeleteSourceItemClick: (source: Source) -> Unit,
               readSource: (sourceId: Int) -> Source,
               updateEditWordState: (editWordState: EditWordState) -> Unit,
               updateSourceState: (source: Source) -> Unit
) {
    Column(modifier = modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
           verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column {
            // TODO: move to string resources
            TitleText(text = "Words grouped by resources")
            ListOfSources(navController = navController,
                          sourcesWithWords = sourcesWithWords,
                          wordsWithTranslations = wordsWithTranslations,
                          onDeleteWordItemClick = onDeleteWordItemClick,
                          onDeleteSourceItemClick = onDeleteSourceItemClick,
                          readSource = readSource,
                          updateEditWordState = updateEditWordState,
                          updateSourceState = updateSourceState,
                          modifier = modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(
            navController = rememberNavController(),
            sourcesWithWords = PreviewData.sourcesWithWords,
            wordsWithTranslations = PreviewData.wordsWithTranslations,
            onDeleteWordItemClick = {},
            onDeleteSourceItemClick = {},
            readSource = { _ -> Source() },
            updateSourceState = { _ -> },
            updateEditWordState = { _ -> }
        )
    }
}
