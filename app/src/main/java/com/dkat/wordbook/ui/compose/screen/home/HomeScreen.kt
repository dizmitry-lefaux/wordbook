package com.dkat.wordbook.ui.compose.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.SourceWithWords
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word_B
import com.dkat.wordbook.ui.compose.source.ListOfSources
import com.dkat.wordbook.ui.theme.AppTheme

@Composable
fun HomeScreen(sources: List<Source>,
               sourcesWithWords: List<SourceWithWords>,
               wordsWithTranslations: List<WordWithTranslations>,
               modifier: Modifier = Modifier,
               readSource: (id: Int) -> Source,
               createWordWithTranslation: (word: Word_B, translation: Translation) -> Unit,
               onDeleteWordItemClick: (word: Word_B) -> Unit,
               onDeleteSourceItemClick: (source: Source) -> Unit,
) {
    Column(modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()),
           verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        InputWord(
            readSourceById = readSource,
            createWordWithTranslation = createWordWithTranslation,
            sources = sources,
        )
        HorizontalDivider(thickness = 4.dp, color = Color.Black)
        Column {
            Text(text = "Words grouped by sources:",
                 modifier = modifier.padding(12.dp),
                 style = MaterialTheme.typography.titleLarge,
                 fontWeight = FontWeight.Bold,
                 textAlign = TextAlign.Left
            )
            ListOfSources(sourcesWithWords = sourcesWithWords,
                          wordsWithTranslations = wordsWithTranslations,
                          onDeleteWordItemClick = onDeleteWordItemClick,
                          onDeleteSourceItemClick = onDeleteSourceItemClick,
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
            sourcesWithWords = PreviewData.sourcesWithWords,
            wordsWithTranslations = PreviewData.wordsWithTranslations,
            sources = PreviewData.sources,
            readSource = { _ -> Source() },
            createWordWithTranslation = { _, _ -> },
            onDeleteWordItemClick = {},
            onDeleteSourceItemClick = {}
        )
    }
}
