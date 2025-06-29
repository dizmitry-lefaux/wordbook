package com.dkat.wordbook.ui.compose.word

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.Word
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.ui.compose.reusable.EditableDeletableItem
import com.dkat.wordbook.ui.compose.screen.Screen
import com.dkat.wordbook.viewModel.screen.EditableWordState

@Composable
fun WordWithTranslationsCard(navController: NavController,
                             wordWithTranslations: WordWithTranslations,
                             readSourceById: (sourceId: Int) -> Source,
                             updateEditableWordState: (editableWordState: EditableWordState) -> Unit,
                             deleteWord: ((word: Word) -> Unit)?,
                             modifier: Modifier
) {
    val word = wordWithTranslations.word
    val translations = wordWithTranslations.translations
    val editableWordState = EditableWordState(
        currentSource = readSourceById(word.sourceId),
        currentWord = word,
        currentTranslations = translations
    )

    Card(modifier = modifier,
         shape = RoundedCornerShape(20.dp),
    ) {
        EditableDeletableItem(navController = navController,
                              editRoute = Screen.EditWord.route,
                              titleValue = word.value,
                              editableObject = editableWordState,
                              updateEditableObject = updateEditableWordState,
                              // TODO: move to string resources
                              editDescription = "edit word",
                              deletableObject = word,
                              deleteObject = deleteWord,
                              // TODO: move to string resources
                              deleteDescription = "remove word",
                              modifier = modifier,
                              additionalContent = {
                                  Column {
                                      // TODO: move to string resources
                                      Text(text = "Translations:", modifier.padding(2.dp))
                                      Column(modifier = Modifier.padding(8.dp)) {
                                          translations.forEach { translation ->
                                              Text(
                                                  text = translation.value,
                                                  modifier = modifier.padding(2.dp),
                                              )
                                          }
                                      }
                                  }
                              }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WordItemPreview1() {
    WordWithTranslationsCard(navController = rememberNavController(),
                             wordWithTranslations = PreviewData.wordWithTranslations1,
                             updateEditableWordState = { _ -> },
                             readSourceById = { _ -> Source() },
                             deleteWord = {},
                             modifier = Modifier
    )
}

@Preview(showBackground = true)
@Composable
fun WordItemPreview2() {
    WordWithTranslationsCard(navController = rememberNavController(),
                             wordWithTranslations = PreviewData.wordWithTranslations1,
                             updateEditableWordState = { _ -> },
                             readSourceById = { _ -> Source() },
                             deleteWord = null,
                             modifier = Modifier
    )
}
