package com.dkat.wordbook.ui.compose.source

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.Word

@Composable
fun ListOfSources(
    sources: List<Source>,
    words: List<Word>,
    onDeleteWordItemClick: (word: Word) -> Unit,
    onDeleteSourceItemClick: (source: Source) -> Unit,
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
                words.filter { word -> word.sourceName == source.name }.toList()
            listOfSources.add(SourceWithWordsData(sourceName = source.name, words = wordsSubList))
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

@Preview(showBackground = true)
@Composable
fun ListOfSourcesPreview() {
    ListOfSources(
        sources = listOf(Source(
            id = 7414,
            name = "Margery Hayes",
            mainOrigLangId = 9818,
            mainTranslationLangId = 7853
        ), Source(
            id = 9027,
            name = "Barbra Hansen",
            mainOrigLangId = 9069,
            mainTranslationLangId = 7478
        )),
        words = listOf(Word(
            id = 6193,
            rusValue = "vehicula",
            engValue = "discere",
            sourceName = "Troy Larson",
            sessionWeight = 4.5f,
            isInSession = false
        )),
        onDeleteWordItemClick = {},
        onDeleteSourceItemClick = {}
    )
}
