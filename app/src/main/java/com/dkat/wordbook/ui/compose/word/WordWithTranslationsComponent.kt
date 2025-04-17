package com.dkat.wordbook.ui.compose.word

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word_B

@Composable
fun WordWithTranslationsItem(
    wordWithTranslations: WordWithTranslations,
    onDeleteWordItemClick: (word: Word_B) -> Unit,
    modifier: Modifier
) {
    val word = wordWithTranslations.word
    val translations = wordWithTranslations.translations
    Column {
        Row {
            Column {
                Text(
                    text = word.value,
                    modifier.padding(8.dp)
                )
            }
            Column(
                modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Button(
                    onClick = { onDeleteWordItemClick(word) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                ) {
                    Image(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "",
                        modifier
                            .background(color = Color.LightGray)
                    )
                }
            }
        }
        Column {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                item {
                    Text(
                        text = "Translations:",
                        modifier.padding(2.dp)
                    )
                }
                items(translations) {
                    Text(
                        text = it.value,
                        modifier = modifier.padding(2.dp),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WordItemPreview()
{
    val wordWithTranslations = WordWithTranslations(
        word = Word_B(id = 6850, sourceId = 1486, languageId = 3567, value = "efficiantur"),
        translations = listOf(
            Translation(id = 2203, wordId = 7627, value = "duo", languageId = 4738),
            Translation(id = 8867, wordId = 5186, value = "necessitatibus", languageId = 6808),
            Translation(id = 7213, wordId = 5834, value = "deterruisset", languageId = 7215)
        )
    )
    WordWithTranslationsItem(
        wordWithTranslations = wordWithTranslations,
        onDeleteWordItemClick = {},
        modifier = Modifier
    )
}
