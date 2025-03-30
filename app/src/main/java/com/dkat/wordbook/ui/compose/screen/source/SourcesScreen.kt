package com.dkat.wordbook.ui.compose.screen.source

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.data.Word
import com.dkat.wordbook.ui.compose.screen.home.WordItem

@Composable
fun SourcesScreen(
    sources: List<String>,
    selectedSourceWords: List<Word>,
    onSourceClick: (sourceName: String) -> Unit,
    onDeleteWordItemClick: (word: Word) -> Unit,
    modifier: Modifier = Modifier
)
{
    Column {
        Column {
            LazyVerticalGrid(
                modifier = modifier.fillMaxSize(),
                columns = GridCells.Fixed(1),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = "Select words source:",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left
                    )
                }
                items(sources) { it ->
                    WordsSource(
                        sourceName = it,
                        modifier = modifier,
                        onSourceClick = { onSourceClick(it) }
                    )
                }
                items(selectedSourceWords) { it ->
                    WordItem(
                        word = it,
                        onDeleteWordItemClick = onDeleteWordItemClick,
                        modifier = modifier,
                    )
                }
            }
        }
    }
}

@Composable
fun WordsSource(
    sourceName: String,
    modifier: Modifier = Modifier,
    onSourceClick: (sourceName: String) -> Unit
)
{
    ElevatedCard(
        modifier = modifier,
        onClick = { onSourceClick(sourceName) }
    ) {
        Text(
            text = sourceName
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SourceScreenPreview()
{
    val sources = listOf("source1", "source2")
    val selectedSourceWords = listOf(
        Word(rusValue = "asdf", engValue = "qwer"),
        Word(rusValue = "asdf2", engValue = "qwer2")
    )
    SourcesScreen(
        sources = sources,
        selectedSourceWords = selectedSourceWords,
        onSourceClick = {},
        onDeleteWordItemClick = {},
        modifier = Modifier
    )
}
