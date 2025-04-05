package com.dkat.wordbook.ui.compose.screen.home

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import com.dkat.wordbook.data.Word
import com.dkat.wordbook.ui.compose.source.ListOfSources
import com.dkat.wordbook.ui.theme.AppTheme

@Composable
fun HomeScreen(
    sources: List<String>,
    words: List<Word>,
    modifier: Modifier = Modifier,
    addWord: (word: Word) -> Unit,
    onDeleteWordItemClick: (word: Word) -> Unit,
    scrollState: ScrollState = rememberScrollState()
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        InputWord(addWord = addWord)
        HorizontalDivider(thickness = 4.dp, color = Color.Black)
        Column {
            Text(
                text = "Words grouped by sources:",
                modifier = modifier.padding(12.dp),
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
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview()
{
    val sources = listOf("source1", "source2")
    val words = listOf(
        Word(
            engValue = "engValue1",
            rusValue = "rusValue1",
            sourceName = "source1"
        ),
        Word(
            engValue = "engValue1",
            rusValue = "rusValue1",
            sourceName = "source2"
        )
    )
    AppTheme {
        HomeScreen(
            sources = sources,
            words = words,
            addWord = {},
            onDeleteWordItemClick = {},
        )
    }
}
