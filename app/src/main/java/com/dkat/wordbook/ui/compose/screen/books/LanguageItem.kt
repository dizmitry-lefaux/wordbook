package com.dkat.wordbook.ui.compose.screen.books

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.ui.compose.reusable.EditableDeletableItem
import com.dkat.wordbook.ui.compose.screen.Screen

private const val TAG = "LanguageItem"

@Composable
fun LanguageItem(navController: NavController,
                 language: Language,
                 deleteLanguage: (language: Language) -> Unit,
                 onDeleteEvent: () -> Unit,
                 updateLanguageState: (language: Language) -> Unit,
                 modifier: Modifier
) {
    Card(modifier = modifier,
         shape = RoundedCornerShape(20.dp)
    ) {
        EditableDeletableItem(navController = navController,
                              editRoute = Screen.EditLanguage.route,
                              titleValue = language.name,
                              editableObject = language,
                              updateEditableObject = updateLanguageState,
                              editDescription = "edit language",
                              onDeleteEvent = onDeleteEvent,
                              deletableObject = language,
                              deleteObject = deleteLanguage,
                              deleteDescription = "delete language",
                              additionalContent = null,
                              modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WordItemPreview() {
    LanguageItem(navController = rememberNavController(),
                 language = PreviewData.language1,
                 deleteLanguage = { _ -> },
                 onDeleteEvent = { },
                 updateLanguageState = { _ -> },
                 modifier = Modifier
    )
}
