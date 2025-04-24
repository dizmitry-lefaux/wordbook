package com.dkat.wordbook.ui.compose.screen.language

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
