package com.dkat.wordbook.ui.compose.screen.home

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.data.SourceWithWordsData
import com.dkat.wordbook.data.Word
import com.dkat.wordbook.ui.theme.AppTheme

@Composable
fun HomeScreen(
    sources: List<String>,
    words: List<Word>,
    modifier: Modifier = Modifier,
    addWord: (word: Word) -> Unit,
    onDeleteWordItemClick: (word: Word) -> Unit,
    scrollState: ScrollState = rememberScrollState()
)
{
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            InputWord(addWord = addWord)
        }
        item {
            Text(
                text = "Words grouped by sources:",
                modifier = modifier.padding(12.dp),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left
            )
        }
        val listOfSources = mutableListOf<SourceWithWordsData>()
        for (source in sources)
        {
            val wordsSubList: List<Word> =
                words.filter { word -> word.sourceName == source }.toList()
            listOfSources.add(SourceWithWordsData(sourceName = source, words = wordsSubList))
        }
        items(listOfSources) {
            SourceWithWords(
                sourceWithWordsData = it,
                modifier = modifier,
                onDeleteWordClick = onDeleteWordItemClick,
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
