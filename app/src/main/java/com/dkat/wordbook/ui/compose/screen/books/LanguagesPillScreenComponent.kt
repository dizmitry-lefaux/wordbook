package com.dkat.wordbook.ui.compose.screen.books

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.ui.compose.reusable.ExpandableSection

@Composable
fun LanguagesPillScreenComponent(modifier: Modifier,
                                 createLanguage: (language: Language) -> Unit,
                                 languages: List<Language>,
                                 navController: NavController,
                                 deleteLanguage: (language: Language) -> Unit,
                                 updateLanguageState: (language: Language) -> Unit
) {
    Column {
        ExpandableSection(
            modifier = modifier.fillMaxWidth(),
            // TODO: move to string resources
            title = "Add new language",
            isHideTitleOnExpand = true,
        ) {
            InputLanguage(createLanguage = createLanguage, languages = languages)
        }
        HorizontalDivider(thickness = 4.dp, color = Color.Black)
        ListOfLanguages(
            navController = navController,
            languages = languages,
            deleteLanguage = deleteLanguage,
            updateLanguageState = updateLanguageState,
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LanguagesPillScreenComponentPreview() {
    LanguagesPillScreenComponent(
        modifier = Modifier,
        createLanguage = { },
        languages = PreviewData.languages,
        navController = rememberNavController(),
        deleteLanguage = { },
        updateLanguageState = { },
    )
}
