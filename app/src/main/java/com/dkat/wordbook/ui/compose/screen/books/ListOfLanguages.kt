package com.dkat.wordbook.ui.compose.screen.books

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Language

@Composable
fun ListOfLanguages(
    navController: NavController,
    languages: List<Language>,
    deleteLanguage: (language: Language) -> Unit,
    updateLanguageState: (language: Language) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier.padding(8.dp)) {
        LazyColumn {
            items(languages) {language ->
                LanguageItem(navController = navController,
                             language = language,
                             deleteLanguage = deleteLanguage,
                             updateLanguageState = updateLanguageState,
                             modifier = modifier
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListOfLanguagesPreview() {
    ListOfLanguages(
        navController = rememberNavController(),
        languages = PreviewData.languages,
        deleteLanguage = {},
        updateLanguageState = {},
        modifier = Modifier
    )
}
