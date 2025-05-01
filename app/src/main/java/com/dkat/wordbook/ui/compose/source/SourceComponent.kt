package com.dkat.wordbook.ui.compose.source

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word_B
import com.dkat.wordbook.ui.compose.reusable.ExpandableSection
import com.dkat.wordbook.ui.compose.word.WordsWithTranslationsList

@Composable
fun ExpandableSourceItem(
    onDeleteSourceClick: (source: Source) -> Unit,
    onDeleteWordClick: (word: Word_B) -> Unit,
    source: Source,
    wordsWithTranslations: List<WordWithTranslations>?,
    modifier: Modifier = Modifier,
) {
    Column {
        ExpandableSection(
            modifier = modifier.fillMaxWidth(),
            title = source.name,
            isHideTitleOnExpand = true,
        ) {
            Column {
                SourceItem(modifier = modifier,
                           source = source,
                           onDeleteSourceClick = onDeleteSourceClick,
                           wordsWithTranslations = wordsWithTranslations,
                           onDeleteWordClick = onDeleteWordClick
                )
                HorizontalDivider(thickness = 8.dp)
            }
        }
    }
}

@Composable
private fun SourceItem(modifier: Modifier,
                       source: Source,
                       onDeleteSourceClick: ((source: Source) -> Unit)?,
                       wordsWithTranslations: List<WordWithTranslations>?,
                       onDeleteWordClick: ((word: Word_B) -> Unit)?
) {
    Column {
        Row {
            Column(modifier = modifier.weight(0.9f),
                   horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = modifier.padding(8.dp),
                    text = source.name,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            if (onDeleteSourceClick != null) {
                Column(horizontalAlignment = Alignment.End) {
                    Button(
                        onClick = { onDeleteSourceClick(source) },
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
            HorizontalDivider(thickness = 2.dp)
            WordsWithTranslationsList(
                wordsWithTranslations = wordsWithTranslations,
                onDeleteWordClick = onDeleteWordClick,
                modifier = modifier,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SourceItemPreview() {
    SourceItem(modifier = Modifier,
               source = PreviewData.source1,
               wordsWithTranslations = PreviewData.wordsWithTranslations,
               onDeleteSourceClick = {},
               onDeleteWordClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun ExpandableSourceItemPreview() {
    ExpandableSourceItem(
        source = PreviewData.source1,
        wordsWithTranslations = PreviewData.wordsWithTranslations,
        onDeleteWordClick = {},
        onDeleteSourceClick = {},
        modifier = Modifier
    )
}
