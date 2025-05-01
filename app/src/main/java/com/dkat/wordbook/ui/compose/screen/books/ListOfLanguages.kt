package com.dkat.wordbook.ui.compose.screen.books

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Language

@Composable
fun ListOfLanguages(
    languages: List<Language>,
    onDeleteLanguageItemClick: (language: Language) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier.padding(8.dp)) {
        languages.forEach { language ->
            LanguageItem(language = language,
                         onDeleteLanguageItemClick = onDeleteLanguageItemClick,
                         modifier = modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListOfLanguagesPreview() {
    ListOfLanguages(languages = PreviewData.languages, onDeleteLanguageItemClick = {})
}
