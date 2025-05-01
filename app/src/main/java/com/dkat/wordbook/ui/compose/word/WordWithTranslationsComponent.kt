package com.dkat.wordbook.ui.compose.word

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word_B

@Composable
fun WordWithTranslationsItem(wordWithTranslations: WordWithTranslations,
                             onDeleteWordItemClick: ((word: Word_B) -> Unit)?,
                             modifier: Modifier
) {
    val word = wordWithTranslations.word
    val translations = wordWithTranslations.translations

    Column {
        Row {
            Column {
                Text(text = word.value, modifier.padding(8.dp))
            }
            if (onDeleteWordItemClick  != null) {
                Column(modifier = modifier.fillMaxWidth(),
                       horizontalAlignment = Alignment.End
                ) {
                    Button(
                        onClick = { onDeleteWordItemClick(word) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    ) {
                        Image(imageVector = Icons.Filled.Clear,
                              contentDescription = "",
                              modifier = modifier.background(color = Color.LightGray)
                        )
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
    WordWithTranslationsItem(wordWithTranslations = PreviewData.wordWithTranslations1,
                             onDeleteWordItemClick = {},
                             modifier = Modifier
    )
}

@Preview(showBackground = true)
@Composable
fun WordItemPreview2() {
    WordWithTranslationsItem(wordWithTranslations = PreviewData.wordWithTranslations1,
                             onDeleteWordItemClick = null,
                             modifier = Modifier
    )
}
