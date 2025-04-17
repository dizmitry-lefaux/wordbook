package com.dkat.wordbook.ui.compose.screen.source

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.SourceWithWords
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word_B
import com.dkat.wordbook.ui.compose.source.ListOfSources

@Composable
fun SourcesScreen(
    sourcesWithWords: List<SourceWithWords>,
    wordsWithTranslations: List<WordWithTranslations>,
    languages: List<Language>,
    createSource: (source: Source) -> Unit,
    onDeleteWordItemClick: (word: Word_B) -> Unit,
    onDeleteSourceItemClick: (source: Source) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState()
) {
    Column {
        InputSource(
            createSource = createSource,
            languages = languages,
            sources = sourcesWithWords.map { it.source }.toList())
        Text(
            modifier = modifier.padding(16.dp),
            text = "Sources:",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left
        )
        ListOfSources(
            onDeleteWordItemClick = onDeleteWordItemClick,
            onDeleteSourceItemClick = onDeleteSourceItemClick,
            modifier = modifier,
            sourcesWithWords = sourcesWithWords,
            wordsWithTranslations = wordsWithTranslations
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SourceScreenPreview()
{
    // TODO: add preview info
}
