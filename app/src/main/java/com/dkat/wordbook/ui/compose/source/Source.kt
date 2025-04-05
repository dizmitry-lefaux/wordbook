package com.dkat.wordbook.ui.compose.source

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.data.Word
import com.dkat.wordbook.ui.compose.expandable.ExpandableSection
import com.dkat.wordbook.ui.compose.word.WordsList

@Composable
fun Source(
    onDeleteWordClick: (word: Word) -> Unit,
    sourceWithWordsData: SourceWithWordsData,
    modifier: Modifier = Modifier,
) {
    ExpandableSection(
        modifier = modifier,
        title = sourceWithWordsData.sourceName
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            HorizontalDivider(thickness = 2.dp)
            WordsList(
                words = sourceWithWordsData.words,
                onDeleteWordClick = onDeleteWordClick,
                modifier = modifier,
            )
        }
        HorizontalDivider(thickness = 8.dp)
    }
}

@Preview(showBackground = true)
@Composable
fun SourceWithWordsPreview()
{
    val sourceName = "source1"
    val words = listOf(
        Word(
            engValue = "engValue1",
            rusValue = "rusValue1",
            sourceName = "source1"
        ),
        Word(
            engValue = "engValue1",
            rusValue = "rusValue1",
            sourceName = "source2"
        )
    )
    val sourceWithWordsData = SourceWithWordsData(
        sourceName = sourceName,
        words = words
    )
    Source(
        sourceWithWordsData = sourceWithWordsData,
        onDeleteWordClick = {},
    )
}
