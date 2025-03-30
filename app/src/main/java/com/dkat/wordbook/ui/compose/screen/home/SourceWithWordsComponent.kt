package com.dkat.wordbook.ui.compose.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.data.SourceWithWordsData
import com.dkat.wordbook.data.Word

@Composable
fun SourceWithWords(
    onDeleteWordClick: (word: Word) -> Unit,
    sourceWithWordsData: SourceWithWordsData,
    modifier: Modifier = Modifier,
)
{
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            "Source: ${sourceWithWordsData.sourceName}",
            modifier = modifier.padding(8.dp),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left
        )
        HorizontalDivider(thickness = 2.dp)
        WordsList(
            words = sourceWithWordsData.words,
            onDeleteWordClick = onDeleteWordClick,
            modifier = modifier,
        )
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
    SourceWithWords(
        sourceWithWordsData = sourceWithWordsData,
        onDeleteWordClick = {},
    )
}
