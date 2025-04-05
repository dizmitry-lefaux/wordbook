package com.dkat.wordbook.ui.compose.source

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.data.Word

@Composable
fun ListOfSources(
    sources: List<String>,
    words: List<Word>,
    onDeleteWordItemClick: (word: Word) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val listOfSources = mutableListOf<SourceWithWordsData>()
        for (source in sources)
        {
            val wordsSubList: List<Word> =
                words.filter { word -> word.sourceName == source }.toList()
            listOfSources.add(SourceWithWordsData(sourceName = source, words = wordsSubList))
        }
        items(listOfSources) {
            Source(
                sourceWithWordsData = it,
                modifier = modifier,
                onDeleteWordClick = onDeleteWordItemClick,
            )
        }
    }
}
