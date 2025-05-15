package com.dkat.wordbook.ui.compose.word

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dkat.wordbook.EditWordState
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word_B
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

    Column(modifier = modifier.fillMaxWidth()) {
        Row {
            // word value
            Column(horizontalAlignment = Alignment.Start) {
                Text(text = word.value, modifier.padding(8.dp))
            }
            Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.End
            ) {
                Row {
                    // edit button
                    Button(
                        onClick = {
                            // TODO: edit translations
                            updateEditWordState(
                                EditWordState(
                                    currentSource = readSourceById(word.sourceId),
                                    currentWord = word,
                                    currentTranslations = translations
                                )
                            )
                            navController.navigate(Screen.EditWord.route)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    ) {
                        Image(imageVector = Icons.Filled.Edit,
                            // TODO: move to string resources
                              contentDescription = "edit word"
                        )
                    }
                    if (onDeleteWordItemClick != null) {
                        // delete button
                        Button(
                            onClick = { onDeleteWordItemClick(word) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        ) {
                            Image(
                                imageVector = Icons.Filled.Clear,
                                // TODO: move to string resources
                                contentDescription = "remove word",
                            )
                        }
                    }
                }
            }
        }
        Column {
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
