package com.dkat.wordbook.ui.compose.language

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.data.entity.Language

// TODO: check why it is a ListOfSources, not ListOfLanguages
@Composable
fun ListOfSources(
    languages: List<Language>,
    onDeleteLanguageItemClick: (language: Language) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(languages) {
            LanguageItem(
                onDeleteLanguageItemClick = onDeleteLanguageItemClick,
                modifier = modifier,
                language = it,
            )
        }
    }
}
