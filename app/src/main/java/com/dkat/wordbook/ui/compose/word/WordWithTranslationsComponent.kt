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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word_B

@Composable
fun WordWithTranslationsItem(wordWithTranslations: WordWithTranslations,
                             readSourceById: (sourceId: Int) -> Source,
                             editWordWithTranslations: (word: Word_B, translations: List<Translation>) -> Unit,
                             onDeleteWordItemClick: ((word: Word_B) -> Unit)?,
                             modifier: Modifier
) {
    var isEditMode by remember { mutableStateOf(false) }
    val word = wordWithTranslations.word
    val translations = wordWithTranslations.translations

    if (!isEditMode) {
        Column(modifier = modifier.fillMaxWidth()) {
            Row {
                // word value
                Column(horizontalAlignment = Alignment.Start) {
                    Text(text = word.value, modifier.padding(8.dp))
                }
                Column(modifier = modifier.fillMaxWidth(),
                       horizontalAlignment = Alignment.End
                ) {
                    Row {
                        // edit button
                        Button(
                            onClick = {
                                isEditMode = true
                                // TODO: edit translations
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
    if (isEditMode) {
        EditWordWithTranslations(
                 source = readSourceById(word.sourceId),
                 inputWord = word,
                 inputTranslations = translations,
                 editWordWithTranslations = editWordWithTranslations,
                 exitEditMode = { isEditMode = false},
                 modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WordItemPreview1() {
    WordWithTranslationsItem(wordWithTranslations = PreviewData.wordWithTranslations1,
                             readSourceById = { _ -> Source() },
                             editWordWithTranslations = { _, _ -> },
                             onDeleteWordItemClick = {},
                             modifier = Modifier
    )
}

@Preview(showBackground = true)
@Composable
fun WordItemPreview2() {
    WordWithTranslationsItem(wordWithTranslations = PreviewData.wordWithTranslations1,
                             readSourceById = { _ -> Source() },
                             editWordWithTranslations = { _, _ -> },
                             onDeleteWordItemClick = null,
                             modifier = Modifier
    )
}
