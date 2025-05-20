package com.dkat.wordbook.ui.compose.screen.books

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.ui.compose.reusable.EditableDeletableItem
import com.dkat.wordbook.ui.compose.screen.Screen

@Composable
fun LanguageItem(navController: NavController,
                 language: Language,
                 onDeleteLanguageItemClick: (language: Language) -> Unit,
                 updateLanguageState: (language: Language) -> Unit,
                 modifier: Modifier
) {
    EditableDeletableItem(navController = navController,
                          editRoute = Screen.EditLanguage.route,
                          titleValue = language.name,
                          editableObject = language,
                          updateEditableObject = updateLanguageState,
                          editDescription = "edit language",
                          deletableObject = language,
                          deleteObject = onDeleteLanguageItemClick,
                          deleteDescription = "delete language",
                          additionalContent = null,
                          modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun WordItemPreview() {
    LanguageItem(navController = rememberNavController(),
                 language = PreviewData.language1,
                 onDeleteLanguageItemClick = { _ -> },
                 updateLanguageState = { _ -> },
                 modifier = Modifier
    )
}
