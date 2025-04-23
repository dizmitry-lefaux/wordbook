package com.dkat.wordbook.ui.compose.source

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word_B
import com.dkat.wordbook.ui.compose.reusable.ExpandableSection
import com.dkat.wordbook.ui.compose.word.WordsWithTranslationsList

@Composable
fun SourceItem(
    onDeleteSourceClick: (source: Source) -> Unit,
    onDeleteWordClick: (word: Word_B) -> Unit,
    source: Source,
    wordsWithTranslations: List<WordWithTranslations>?,
    modifier: Modifier = Modifier,
) {
    Row {
        Column {
            ExpandableSection(
                modifier = modifier,
                title = source.name
            ) {
                Column(modifier = modifier.fillMaxWidth()) {
                    HorizontalDivider(thickness = 2.dp)
                    WordsWithTranslationsList(
                        wordsWithTranslations = wordsWithTranslations,
                        onDeleteWordClick = onDeleteWordClick,
                        modifier = modifier,
                    )
                }
                HorizontalDivider(thickness = 8.dp)
            }
        }
        Column(
            modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            // TODO: make 'Clear' image visible
            Button(
                onClick = { onDeleteSourceClick(source) },
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
}

@Preview(showBackground = true)
@Composable
fun SourcePreview() {
    SourceItem(
        onDeleteWordClick = {},
        onDeleteSourceClick = {},
        source = Source(
            id = 3771,
            name = "Stacey Hayden",
            mainOrigLangId = 8798,
            mainTranslationLangId = 6743
        ),
        wordsWithTranslations = listOf(
            WordWithTranslations(
                word = Word_B(id = 4428, sourceId = 4887, languageId = 9685, value = "dictas"),
                translations = listOf(
                    Translation(id = 5050, wordId = 3924, value = "detraxit", languageId = 2641),
                    Translation(id = 3907, wordId = 7219, value = "hendrerit", languageId = 5243)
                )
            ),
            WordWithTranslations(
                word = Word_B(id = 6426, sourceId = 9615, languageId = 7799, value = "nullam"),
                translations = listOf(
                    Translation(id = 1393, wordId = 2937, value = "consequat", languageId = 4081),
                    Translation(id = 6075, wordId = 2539, value = "tellus", languageId = 3243),
                    Translation(id = 6495, wordId = 7035, value = "consequat", languageId = 9676)
                )
            ),
            WordWithTranslations(
                word = Word_B(id = 6426, sourceId = 9615, languageId = 7799, value = "nullam"),
                translations = listOf(
                    Translation(id = 1393, wordId = 2937, value = "consequat", languageId = 4081),
                    Translation(id = 6075, wordId = 2539, value = "tellus", languageId = 3243)
                )
            )
        ),
        modifier = Modifier
    )
}
