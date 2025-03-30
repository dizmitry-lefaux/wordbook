package com.dkat.wordbook.ui.compose.screen.session

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.data.Word
import com.dkat.wordbook.ui.compose.card.CardWithAnimatedAlpha

@Composable
fun SessionScreen(
    sessionWords: List<Word>,
    onRestartSessionClick: () -> Unit,
    onUpdateSessionClick: () -> Unit,
    modifier: Modifier = Modifier
)
{
    Column() {
        Column(Modifier.fillMaxWidth()) {
            Row(Modifier.fillMaxWidth()) {
                Button(
                    modifier = modifier.padding(16.dp),
                    onClick = onUpdateSessionClick
                ) {
                    Text(
                        text = "Update session",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Button(
                    modifier = modifier.padding(16.dp),
                    onClick = onRestartSessionClick
                ) {
                    Text(
                        text = "Restart session",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
        HorizontalDivider()
        HorizontalDivider()
        if (sessionWords.isNotEmpty())
        {
            Column {
                HorizontalDivider()
                LazyVerticalGrid(
                    modifier = modifier.fillMaxSize(),
                    columns = GridCells.Fixed(1),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(sessionWords) {
                        CardWithAnimatedAlpha(it)
                    }
                }
            }
        }
        if (sessionWords.isEmpty())
        {
            Text(
                text = "There are no words left.\nPlease, start new session.",
                modifier = modifier.padding(12.dp),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SessionScreenWithValuesPreview(
    words: List<Word> = listOf(
        Word(
            id = 8643,
            rusValue = "ne",
            engValue = "possit",
            sourceName = "Ron Hobbs",
            sessionWeight = 2.3f,
            isInSession = true
        ),
        Word(
            id = 4794,
            rusValue = "omittam",
            engValue = "ea",
            sourceName = "Stacey Weber",
            sessionWeight = 6.7f,
            isInSession = true
        )
    )
)
{
    SessionScreen(
        sessionWords = words,
        onUpdateSessionClick = { },
        onRestartSessionClick = { },
        modifier = Modifier
    )
}

@Preview(showBackground = true)
@Composable
fun SessionScreenEmptyPreview(
    words: List<Word> = emptyList()
)
{
    SessionScreen(
        sessionWords = words,
        onUpdateSessionClick = { },
        onRestartSessionClick = { },
        modifier = Modifier
    )
}
