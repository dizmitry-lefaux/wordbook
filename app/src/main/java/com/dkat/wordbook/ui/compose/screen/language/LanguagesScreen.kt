package com.dkat.wordbook.ui.compose.screen.language

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.dkat.wordbook.data.entity.Language

@Composable
fun LanguagesScreen(
    languages: List<Language>,
    createLanguage: (language: Language) -> Unit,
    onDeleteLanguageItemClick: (language: Language) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState()
) {
    Column {
        InputLanguage(
            createLanguage = createLanguage,
            languages = languages
        )
        Text(
            modifier = modifier.padding(16.dp),
            text = "Languages:",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left
        )
        LazyVerticalGrid(
            modifier = modifier.fillMaxSize(),
            columns = GridCells.Fixed(1),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(languages) {
                LanguageItem(
                    language = it,
                    onDeleteLanguageItemClick = onDeleteLanguageItemClick,
                    modifier = modifier,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SourceScreenPreview()
{
    val languages = listOf(
        Language(id = 4426, name = "English"),
        Language(id = 4427, name = "Russian"),
    )
    LanguagesScreen(
        languages = languages,
        createLanguage = {},
        onDeleteLanguageItemClick = {},
        modifier = Modifier
    )
}
