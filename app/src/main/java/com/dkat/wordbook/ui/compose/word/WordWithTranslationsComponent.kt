package com.dkat.wordbook.ui.compose.word

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dkat.wordbook.viewModel.EditWordState
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word_B
import com.dkat.wordbook.ui.compose.reusable.EditableDeletableItem
import com.dkat.wordbook.ui.compose.screen.Screen

@Composable
fun WordWithTranslationsItem(navController: NavController,
                             wordWithTranslations: WordWithTranslations,
                             readSourceById: (sourceId: Int) -> Source,
                             updateEditWordState: (editWordState: EditWordState) -> Unit,
                             onDeleteWordItemClick: ((word: Word_B) -> Unit)?,
                             modifier: Modifier
) {
    val word = wordWithTranslations.word
    val translations = wordWithTranslations.translations
    val editWordState = EditWordState(
        currentSource = readSourceById(word.sourceId),
        currentWord = word,
        currentTranslations = translations
    )

    EditableDeletableItem(navController = navController,
                          editRoute = Screen.EditWord.route,
                          titleValue = word.value,
                          editObject = editWordState,
                          updateEditObjectState = updateEditWordState,
                          // TODO: move to string resources
                          editDescription = "edit word",
                          deleteObject = word,
                          onDeleteObjectClick = onDeleteWordItemClick,
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

@Preview(showBackground = true)
@Composable
fun WordItemPreview1() {
    WordWithTranslationsItem(navController = rememberNavController(),
                             wordWithTranslations = PreviewData.wordWithTranslations1,
                             updateEditWordState = { _ -> },
                             readSourceById = { _ -> Source() },
                             onDeleteWordItemClick = {},
                             modifier = Modifier
    )
}

@Preview(showBackground = true)
@Composable
fun WordItemPreview2() {
    WordWithTranslationsItem(navController = rememberNavController(),
                             wordWithTranslations = PreviewData.wordWithTranslations1,
                             updateEditWordState = { _ -> },
                             readSourceById = { _ -> Source() },
                             onDeleteWordItemClick = null,
                             modifier = Modifier
    )
}
