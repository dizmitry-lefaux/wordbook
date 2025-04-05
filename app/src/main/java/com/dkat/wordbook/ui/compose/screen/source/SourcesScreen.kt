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
import com.dkat.wordbook.data.Word
import com.dkat.wordbook.ui.compose.source.ListOfSources

@Composable
fun SourcesScreen(
    sources: List<String>,
    words: List<Word>,
    onDeleteWordItemClick: (word: Word) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState()
) {
    Column {
        Text(
            modifier = modifier.padding(16.dp),
            text = "Sources:",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left
        )
        ListOfSources(
            sources = sources,
            words = words,
            onDeleteWordItemClick = onDeleteWordItemClick,
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SourceScreenPreview()
{
    val sources = listOf("source1", "source 2")
    val words = listOf(
        Word(rusValue = "asdf", engValue = "qwer"),
        Word(rusValue = "asdf2", engValue = "qwer2")
    )
    SourcesScreen(
        sources = sources,
        onDeleteWordItemClick = {},
        words = words,
        modifier = Modifier
    )
}
